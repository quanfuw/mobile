//
//  PhotosViewController.h
//  SampleFlickrClient
//
//  Created by exo on 2/24/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class PhotoDetailViewController;

@interface PhotosViewController : UITableViewController

@property (nonatomic, retain) NSArray *photos;
@property (nonatomic, assign) BOOL recentsView;
@property (nonatomic, retain) PhotoDetailViewController *photoDetailController;

@end
