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

@implementation PlacesViewController

@synthesize places = _places;
@synthesize photosController = _photosController;

- (void)dealloc {
    [_places release];
    [_photosController release];
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
        self.navigationItem.title = @"Places";
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
    [super viewDidUnload];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    [SVProgressHUD showWithStatus:@"Fetching..." maskType:SVProgressHUDMaskTypeGradient];
    dispatch_queue_t concurrentQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_async(concurrentQueue, ^{
        self.places = [FlickrFetcher topPlaces];
        dispatch_sync(dispatch_get_main_queue(), ^{
            [self.tableView reloadData]; 
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

#pragma mark - getter & setter
- (PhotosViewController *)photosController {
    if (_photosController == nil)
        _photosController = [[PhotosViewController alloc] initWithNibName:@"PhotosViewController" bundle:nil];
    return _photosController;
}

#pragma mark - Table view data source

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
    NSString *location = [place objectForKey:@"_content"];
    cell.textLabel.text = [location substringToIndex:[location rangeOfString:@","].location];
    cell.detailTextLabel.text = location;
    
    return cell;
}


#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSDictionary *place = [self.places objectAtIndex:indexPath.row];
    [SVProgressHUD showWithStatus:@"Fetching..." maskType:SVProgressHUDMaskTypeGradient];
    dispatch_queue_t concurrentQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_async(concurrentQueue, ^{
        self.photosController.photos = [FlickrFetcher photosInPlace:place maxResults:50];
        dispatch_sync(dispatch_get_main_queue(), ^ {
            [self.photosController.tableView reloadData];
            [self.navigationController pushViewController:self.photosController animated:YES];
            [SVProgressHUD dismiss];            
        });
    });
    
}

@end
