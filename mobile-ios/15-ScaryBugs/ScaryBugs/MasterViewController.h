//
//  MasterViewController.h
//  ScaryBugs
//
//  Created by exo on 2/21/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class DetailViewController;

@interface MasterViewController : UITableViewController

@property (strong, nonatomic) DetailViewController *detailViewController;
@property (strong, nonatomic) NSMutableArray *bugs;
@property (retain, nonatomic) IBOutlet UIBarButtonItem *myAddButtonItem;
- (IBAction)addTapped:(id)sender;

@end
