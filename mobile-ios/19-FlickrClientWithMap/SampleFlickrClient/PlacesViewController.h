//
//  PlacesViewController.h
//  SampleFlickrClient
//
//  Created by exo on 2/24/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>

@interface PlacesViewController : UITableViewController <MKMapViewDelegate, UITableViewDelegate>

@property (nonatomic, assign) BOOL displayMap;
@property (retain, nonatomic) IBOutlet UIBarButtonItem *switchViewItem;
@property (nonatomic, retain) NSArray *places;
@property (retain, nonatomic) IBOutlet MKMapView *mapView;
@property (retain, nonatomic) IBOutlet UITableView *placesTableView;

- (void)reloadView;
- (IBAction)switchView:(id)sender;

@end
