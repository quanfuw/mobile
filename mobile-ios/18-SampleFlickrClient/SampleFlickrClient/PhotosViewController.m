//
//  PhotosViewController.m
//  SampleFlickrClient
//
//  Created by exo on 2/24/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PhotosViewController.h"
#import "PhotoDetailViewController.h"
#import "SVProgressHUD.h"
#import "FlickrFetcher.h"
#import "RecentPhotosManagement.h"

@interface PhotosViewController () 
- (NSString*)getTitleOfPhoto:(NSDictionary*)photo;
- (NSString*)getDescriptionOfPhoto:(NSDictionary*)photo;
@end

@implementation PhotosViewController

@synthesize photos = _photos;
@synthesize photoDetailController = _photoDetailController;
@synthesize recentsView = _recentsView;

- (void)dealloc {
    [_photos release];
    [_photoDetailController release];
    [super dealloc];
}

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    if (self.recentsView) {
        self.navigationItem.title = @"Recents";
    }
    self.photoDetailController = [[[PhotoDetailViewController alloc] initWithNibName:@"PhotoDetailViewController" bundle:nil] autorelease];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    self.photoDetailController = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    if (self.recentsView) {
        self.photos = [RecentPhotosManagement getRecentPhotos];
        [self.tableView reloadData];
    }
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return YES;
}

#pragma mark - getter & setter


#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.photos.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"MyPhotoCell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier] autorelease];
    }
    NSDictionary *photo = [self.photos objectAtIndex:indexPath.row];
    cell.textLabel.text = [self getTitleOfPhoto:photo];
    cell.detailTextLabel.text = [self getDescriptionOfPhoto:photo];
    return cell;
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSDictionary *photo = [self.photos objectAtIndex:indexPath.row];
    [SVProgressHUD showWithStatus:@"Fetching..." maskType:SVProgressHUDMaskTypeGradient];
    dispatch_queue_t concurrentQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_async(concurrentQueue, ^{
        NSURL *imgUrl = [FlickrFetcher urlForPhoto:photo format:FlickrPhotoFormatLarge];
        UIImage *image = [UIImage imageWithData:[NSData dataWithContentsOfURL:imgUrl]];
        dispatch_sync(dispatch_get_main_queue(), ^{
            self.photoDetailController.photo = photo;
            [SVProgressHUD dismiss];
            [self.navigationController pushViewController:self.photoDetailController animated:YES];
            self.photoDetailController.navigationItem.title = [self getTitleOfPhoto:photo];
            [self.photoDetailController displayImage:image];
        });
    });
}

#pragma mark - utilities 

- (NSString *)getDescriptionOfPhoto:(NSDictionary *)photo {
    return [[photo objectForKey:@"description"] objectForKey:@"_content"];
}

- (NSString*)getTitleOfPhoto:(NSDictionary *)photo {
    NSString *title = [photo objectForKey:@"title"];
    NSString *description = [self getDescriptionOfPhoto:photo];
    if (title == nil) {
        if (description && [description length] == 0) 
            title = [NSString stringWithString:description];
        else title = @"Unknown";
    }
    return title;
}

@end
