//
//  PhotoCoreDataTableViewController.h
//  SampleFlickrClient
//
//  Created by exo on 3/7/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CoreDataTableViewController.h"

@class Place, Tag;

@interface PhotosCoreDataTableViewController : CoreDataTableViewController

@property (nonatomic, retain) Place *place;
@property (nonatomic, retain) Tag *tag;

@end
