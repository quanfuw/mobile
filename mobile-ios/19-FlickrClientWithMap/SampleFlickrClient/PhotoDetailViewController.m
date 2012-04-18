//
//  PhotoDetailViewController.m
//  SampleFlickrClient
//
//  Created by exo on 2/24/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <CoreData/CoreData.h>
#import "PhotoDetailViewController.h"
#import "FlickrFetcher.h"
#import "CacheManager.h"
#import "SVProgressHUD.h"
#import "SFCUtils.h"
#import "CacheManager.h"
#import "VacationHelper.h"
#import "Photo+Flickr.h"

static NSString * const MY_VACATION = @"MyVacation";

@interface PhotoDetailViewController () 
- (void)setMaxMinZoomScalesForCurrentBounds;
@end

@implementation PhotoDetailViewController

@synthesize photo = _photo;
@synthesize photoVisited = _photoVisited;
@synthesize imageView = _imageView;
@synthesize scrollView = _scrollView;
@synthesize visitButton = _visitButton;

- (void)dealloc {
    [_photo release];
    [_imageView release];
    [_scrollView release];
    [_visitButton release];
    [super dealloc];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        _scrollView.showsHorizontalScrollIndicator = NO;
        _scrollView.showsVerticalScrollIndicator = NO;
        _scrollView.bouncesZoom = YES;
        _scrollView.decelerationRate = UIScrollViewDecelerationRateFast;
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

#pragma mark - View lifecycle
- (void)checkVisitOrUnvisit {
    [VacationHelper openVacation:MY_VACATION usingBlock:^(UIManagedDocument *document) {
        if ([Photo photoIsExisted:self.photo inManagedObjectContext:document.managedObjectContext]) {
            self.photoVisited = YES;
            self.visitButton.title = @"Unvisits";
            
        } else {
            self.photoVisited = NO;
            self.visitButton.title = @"Visits";
        }
    }];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationItem.rightBarButtonItem = self.visitButton;
}

- (void)viewDidUnload
{
    [self setImageView:nil];
    [self setScrollView:nil];
    [self setVisitButton:nil];
    [super viewDidUnload];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [self checkVisitOrUnvisit];
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    [SVProgressHUD showWithStatus:@"Fetching..."];
    dispatch_queue_t concurrentQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_async(concurrentQueue, ^{
        NSString *imagePath = [CacheManager getImageCachePath:[self.photo objectForKey:@"id"]];
        NSData *data = nil;
        if (imagePath) {
            NSLog(@"loading image from cache...");
            data = [NSData dataWithContentsOfFile:imagePath];
        } else {
            NSLog(@"fetching image from network...");
            NSURL *imgUrl = [FlickrFetcher urlForPhoto:self.photo format:FlickrPhotoFormatLarge];
            data = [NSData dataWithContentsOfURL:imgUrl];
            dispatch_queue_t cacheQueue = dispatch_queue_create("image caching thread", NULL);
            dispatch_async(cacheQueue, ^{
                [CacheManager cacheImage:data imageId:[self.photo objectForKey:@"id"] error:nil];
            });
            dispatch_release(cacheQueue);
            
        }
        UIImage *image = [UIImage imageWithData:data];
        dispatch_sync(dispatch_get_main_queue(), ^{
            [self displayImage:image];
            self.navigationItem.title = [SFCUtils getTitleOfPhoto:self.photo];
            [CacheManager pushPhotoToList:self.photo];
            [SVProgressHUD dismiss];
        });
    });
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return YES;
}

- (IBAction)visitOrUnvisit:(id)sender {
    if (!self.photoVisited) {
        [VacationHelper openVacation:MY_VACATION usingBlock:^(UIManagedDocument *document) {
            if ([Photo photoWithFlickrInfo:self.photo inManagedObjectContext:document.managedObjectContext]) {
                self.photoVisited = YES;
                self.visitButton.title = @"Unvisits";
            };
            
        }];
    } else {
        [VacationHelper openVacation:MY_VACATION usingBlock:^(UIManagedDocument *document) {
            Photo *photo = [Photo photoWithFlickrInfo:self.photo inManagedObjectContext:document.managedObjectContext];
            if (photo) {
                NSEnumerator *tagsEnum = [photo.tags objectEnumerator];
                Tag *tag = nil;
                while (tag = [tagsEnum nextObject]) {
                    tag.photosNumber = [NSNumber numberWithInt:([tag.photosNumber intValue] - 1)];
                } 
                [document.managedObjectContext deleteObject:photo];
                self.photoVisited = NO;
                self.visitButton.title = @"Visits";
            }
        }];
    }
}

- (void)displayImage:(UIImage *)image {
    // clear previous image view
    [self.imageView removeFromSuperview];
    self.imageView = nil;
    
    // reset our zoomscale to 1.0 before doing any further calculations
    self.scrollView.zoomScale = 1.0;
    
    // make a new UIImageView for the new image
    self.imageView = [[[UIImageView alloc] initWithImage:image] autorelease];
    [self.scrollView addSubview:self.imageView];
    self.scrollView.contentSize = image.size;
    [self setMaxMinZoomScalesForCurrentBounds];
    self.scrollView.zoomScale = self.scrollView.minimumZoomScale;
}


#pragma mark - scroll view delegate 

- (UIView *)viewForZoomingInScrollView:(UIScrollView *)scrollView {
    return self.imageView;
}

- (void)setMaxMinZoomScalesForCurrentBounds {
    CGSize boundSize = self.scrollView.bounds.size;
    CGSize imageSize = self.imageView.bounds.size;
    CGFloat xScale = boundSize.width / imageSize.width;
    CGFloat yScale = boundSize.height / imageSize.height;
    CGFloat minScale = MIN(xScale, yScale);
    CGFloat maxScale = 1.0 / [[UIScreen mainScreen] scale];
    if (minScale > maxScale) 
        minScale = maxScale;
    self.scrollView.maximumZoomScale = maxScale;
    self.scrollView.minimumZoomScale = minScale;
}




@end
