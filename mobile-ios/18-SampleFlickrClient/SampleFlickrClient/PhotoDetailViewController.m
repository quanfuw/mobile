//
//  PhotoDetailViewController.m
//  SampleFlickrClient
//
//  Created by exo on 2/24/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PhotoDetailViewController.h"
#import "FlickrFetcher.h"
#import "RecentPhotosManagement.h"

@implementation PhotoDetailViewController


@synthesize photo = _photo;
@synthesize imageView = _imageView;
@synthesize scrollView = _scrollView;

- (void)dealloc {
    [_photo release];
    [_imageView release];
    [_scrollView release];
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

- (void)viewDidLoad
{
    [super viewDidLoad];
}

- (void)viewDidUnload
{
    [self setImageView:nil];
    [self setScrollView:nil];
    [super viewDidUnload];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [RecentPhotosManagement pushPhotoToList:self.photo];
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return YES;
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
    self.scrollView.maximumZoomScale = 2.0;
    self.scrollView.minimumZoomScale = 0.5;
}


#pragma mark - scroll view delegate 

- (UIView *)viewForZoomingInScrollView:(UIScrollView *)scrollView {
    return self.imageView;
}


@end
