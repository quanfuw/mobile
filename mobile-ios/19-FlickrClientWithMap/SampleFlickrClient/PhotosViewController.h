//
//  PhotosViewController.h
//  SampleFlickrClient
//
//  Created by exo on 2/24/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>

@class PhotoDetailViewController;

@interface PhotosViewController : UITableViewController <MKMapViewDelegate>

@property (nonatomic, assign) NSDictionary *place;
@property (nonatomic, retain) NSArray *photos;
// Flag to check recent photos view or not
@property (nonatomic, assign) BOOL recentsView;
@property (nonatomic, assign) BOOL displayMap;
@property (retain, nonatomic) IBOutlet MKMapView *photoMapView;
@property (retain, nonatomic) IBOutlet UITableView *photosTableView;
@property (retain, nonatomic) IBOutlet UIBarButtonItem *switchViewButton;

- (IBAction)switchView:(id)sender;
- (void)reloadView;

@end
