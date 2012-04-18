//
//  PlacesViewController.h
//  SampleFlickrClient
//
//  Created by exo on 2/24/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class PhotosViewController;

@interface PlacesViewController : UITableViewController

@property (nonatomic, retain) NSArray *places;
@property (nonatomic, retain) PhotosViewController *photosController;

@end
