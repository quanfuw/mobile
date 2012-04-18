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
#import "CacheManager.h"
#import "SFCUtils.h"
#import "MapPoint.h"

@interface PhotosViewController () 
- (void)showPhotoDetailsViewWithPhoto:(NSDictionary*)photo;
- (void)loadMapView;
@end

@implementation PhotosViewController

@synthesize photos = _photos;
@synthesize recentsView = _recentsView;
@synthesize displayMap = _displayMap;
@synthesize photoMapView = _photoMapView;
@synthesize photosTableView = _photosTableView;
@synthesize switchViewButton = _switchViewButton;
@synthesize place = _place;

- (void)dealloc {
    [_photos release];
    [_photoMapView release];
    [_switchViewButton release];
    [_photosTableView release];
    [super dealloc];
}

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
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
    self.navigationItem.rightBarButtonItem = self.switchViewButton;
    if (self.recentsView) {
        self.navigationItem.title = @"Recents";
    }
}

- (void)viewDidUnload
{
    [self setPhotoMapView:nil];
    [self setSwitchViewButton:nil];
    [self setPhotosTableView:nil];
    [super viewDidUnload];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    if (self.recentsView) {
        self.photos = [CacheManager getRecentPhotos];
        [self reloadView];
    }
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    if (!self.recentsView) {
        [SVProgressHUD showWithStatus:@"Fetching..."];
        dispatch_queue_t concurrentQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
        dispatch_async(concurrentQueue, ^{
            self.photos = [FlickrFetcher photosInPlace:self.place maxResults:50];
            dispatch_sync(dispatch_get_main_queue(), ^ {
                [self reloadView];
                [SVProgressHUD dismiss];            
            });
        });        
    }

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

- (void)showPhotoDetailsViewWithPhoto:(NSDictionary *)photo {
    PhotoDetailViewController *photoDetailController = [[PhotoDetailViewController alloc] initWithNibName:@"PhotoDetailViewController" bundle:nil];
    photoDetailController.photo = photo;
    [self.navigationController pushViewController:photoDetailController animated:YES];
    [photoDetailController release];
}

#pragma mark - getter & setter


#pragma mark - Table view data source

- (IBAction)switchView:(id)sender {
    self.displayMap = !self.displayMap;
    if (self.displayMap) {
        self.view = self.photoMapView;
    } else {
        self.view = self.photosTableView;
        [self.tableView reloadData];
    }
    [self reloadView];
}

- (void)loadMapView {
    [self.photoMapView removeAnnotations:self.photoMapView.annotations];
    double centerLat = 0.0, centerLong = 0.0, maxLatDelta = 0.0, maxLongDelta = 0.0;
    for (int i = 0; i < self.photos.count; i++) {
        NSDictionary *photo = [self.photos objectAtIndex:i];
        double latitude = [[photo objectForKey:LATITUDE] doubleValue];
        double longtitude = [[photo objectForKey:LONGITUDE] doubleValue];
        if (i == 0) {
            centerLat = latitude;
            centerLong = longtitude;
        } else {
            double distanceLat = ABS(latitude - centerLat);
            double distanceLong = ABS(longtitude - centerLong);
            if (distanceLat > maxLatDelta) maxLatDelta = distanceLat;
            if (distanceLong > maxLongDelta) maxLongDelta = distanceLong;
        }
        
        CLLocationCoordinate2D ordinate;
        ordinate.latitude = latitude;
        ordinate.longitude = longtitude;
        MapPoint *point = [[MapPoint alloc] initWithCoordinate:ordinate title:[SFCUtils getTitleOfPhoto:photo] subtitle:[SFCUtils getDescriptionOfPhoto:photo] data:photo];
        [self.photoMapView addAnnotation:point];
        [point release];
    }
    
    MKCoordinateRegion startRegion;
    startRegion.center.latitude = centerLat;
    startRegion.center.longitude = centerLong;
    startRegion.span.latitudeDelta = maxLatDelta * 2;
    startRegion.span.longitudeDelta = maxLongDelta * 2;
    [self.photoMapView setRegion:startRegion animated:YES];
    
}

- (void)reloadView {
    if (self.displayMap) {
        [self.photosTableView removeFromSuperview];
        self.switchViewButton.title = @"List";
        [self loadMapView];
        self.view = self.photoMapView;
    } else {
        self.switchViewButton.title = @"Map";
        [self.photoMapView removeFromSuperview];
        self.view = self.photosTableView;
        [self.tableView reloadData];
    }
}

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
    cell.textLabel.text = [SFCUtils getTitleOfPhoto:photo];
    cell.detailTextLabel.text = [SFCUtils getDescriptionOfPhoto:photo];
    return cell;
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSDictionary *photo = [self.photos objectAtIndex:indexPath.row];
    [self showPhotoDetailsViewWithPhoto:photo];
}

#pragma mark - Map view delegate 

- (MKAnnotationView *)mapView:(MKMapView *)mapView viewForAnnotation:(id<MKAnnotation>)annotation {
    if ([annotation isKindOfClass:[MapPoint class]]) {
        static NSString *photoPointIdentifier = @"photopointidentifier";
        MKPinAnnotationView *annotationView = (MKPinAnnotationView*) [self.photoMapView dequeueReusableAnnotationViewWithIdentifier:photoPointIdentifier];
        if (!annotationView) {
            annotationView = [[[MKPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:photoPointIdentifier] autorelease];
            annotationView.pinColor = MKPinAnnotationColorRed;
            annotationView.animatesDrop = YES;
            annotationView.canShowCallout = YES;
            UIButton *disclosureButton = [UIButton buttonWithType:UIButtonTypeDetailDisclosure];
            annotationView.rightCalloutAccessoryView = disclosureButton;
        }
        return annotationView;
        
    } 
    return nil;
}

- (void)mapView:(MKMapView *)mapView annotationView:(MKAnnotationView *)view calloutAccessoryControlTapped:(UIControl *)control {
    NSDictionary *photo = ((MapPoint*) view.annotation).data;
    [self showPhotoDetailsViewWithPhoto:photo];
}

- (void)mapView:(MKMapView *)mapView didSelectAnnotationView:(MKAnnotationView *)view {
    NSDictionary *photo = ((MapPoint*) view.annotation).data;
    dispatch_queue_t concurrentQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_async(concurrentQueue, ^{
        NSURL *imgUrl = [FlickrFetcher urlForPhoto:photo format:FlickrPhotoFormatSquare];
        UIImage *image = [UIImage imageWithData:[NSData dataWithContentsOfURL:imgUrl]];
        dispatch_sync(dispatch_get_main_queue(), ^{
            UIImageView *imageView = [[UIImageView alloc] initWithImage:image];
            view.leftCalloutAccessoryView = imageView;
            [imageView release];
        });
    });
}

@end
