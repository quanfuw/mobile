//
//  PlacesViewController.m
//  SampleFlickrClient
//
//  Created by exo on 2/24/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PlacesViewController.h"
#import "PhotosViewController.h"
#import "FlickrFetcher.h"
#import "SVProgressHUD.h"
#import "SFCUtils.h"
#import "MapPoint.h"

@interface PlacesViewController () 
- (void)showPhotosControllerWithPlace:(NSDictionary*)place;
- (void)loadMapView;
@end

@implementation PlacesViewController

@synthesize displayMap = _displayMap;
@synthesize switchViewItem = _switchViewItem;
@synthesize places = _places;
@synthesize mapView = _mapView;
@synthesize placesTableView = _placesTableView;

- (void)dealloc {
    [_places release];
    [_switchViewItem release];
    [_mapView release];
    [_placesTableView release];
    [super dealloc];
}

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        
    }
    return self;
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
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
    self.navigationItem.title = @"Places";
    self.navigationItem.rightBarButtonItem = self.switchViewItem;
}

- (void)viewDidUnload
{
    [self setSwitchViewItem:nil];
    [self setMapView:nil];
    [self setPlacesTableView:nil];
    [super viewDidUnload];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    [SVProgressHUD showWithStatus:@"Fetching..."];
    dispatch_queue_t concurrentQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_async(concurrentQueue, ^{
        self.places = [FlickrFetcher topPlaces];
        dispatch_sync(dispatch_get_main_queue(), ^{
            [self reloadView];
            [SVProgressHUD dismiss];
        });
    });
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

- (void)showPhotosControllerWithPlace:(NSDictionary *)place {
    PhotosViewController *photosController = [[PhotosViewController alloc] initWithNibName:@"PhotosViewController" bundle:nil];
    photosController.place = place;    
    [self.navigationController pushViewController:photosController animated:YES];
    [photosController release];
}

#pragma mark - getter & setter

#pragma mark - Table view data source

- (void)loadMapView {
    [self.mapView removeAnnotations:self.mapView.annotations];
    double centerLat = 0.0, centerLong = 0.0, maxLatDelta = 0.0, maxLongDelta = 0.0;
    for (int i = 0; i < self.places.count; i++) {
        NSDictionary *place = [self.places objectAtIndex:i];
        double latitude = [[place objectForKey:LATITUDE] doubleValue];
        double longtitude = [[place objectForKey:LONGITUDE] doubleValue];
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
        MapPoint *point = [[MapPoint alloc] initWithCoordinate:ordinate title:[SFCUtils getCityNameOfPlace:place] subtitle:[SFCUtils getLocationOfPlace:place] data:place];
        [self.mapView addAnnotation:point];
        [point release];
    }
    MKCoordinateRegion startRegion;
    startRegion.center.latitude = centerLat;
    startRegion.center.longitude = centerLong;
    startRegion.span = MKCoordinateSpanMake(MIN(maxLatDelta * 2, 180.0), MIN(maxLongDelta * 2, 180.0));
    [self.mapView setRegion:startRegion];
    
}

- (void)reloadView {
    if (self.displayMap) {
        self.switchViewItem.title = @"List";
        [self loadMapView];
    } else {
        self.switchViewItem.title = @"Map";
        [self.tableView reloadData];        
    }
}

- (IBAction)switchView:(id)sender {
    self.displayMap = !self.displayMap;
    if (self.displayMap) {
        self.view = self.mapView;
    } else {
        self.view = self.placesTableView;
    }
    [self reloadView];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.places.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"MyPlaceCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier] autorelease];
    }
    
    NSDictionary *place = [self.places objectAtIndex:indexPath.row];
    NSString *location = [SFCUtils getLocationOfPlace:place];
    cell.textLabel.text = [SFCUtils getCityNameOfPlace:place];
    cell.detailTextLabel.text = location;
    
    return cell;
}


#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [self showPhotosControllerWithPlace:[self.places objectAtIndex:indexPath.row]];
}

#pragma mark - MK Map View delegate 

- (MKAnnotationView *)mapView:(MKMapView *)mapView viewForAnnotation:(id<MKAnnotation>)annotation {
    if ([annotation isKindOfClass:[MapPoint class]]) {
        static NSString *placePointIdentifier = @"placepointidentifier";
        MKPinAnnotationView *annotationView = (MKPinAnnotationView*) [self.mapView dequeueReusableAnnotationViewWithIdentifier:placePointIdentifier];
        if (!annotationView) {
            annotationView = [[[MKPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:placePointIdentifier] autorelease];
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
    MapPoint *placePoint = view.annotation;
    [self showPhotosControllerWithPlace:placePoint.data];
}

@end
