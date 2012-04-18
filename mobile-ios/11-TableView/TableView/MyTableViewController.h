//
//  MyTableViewController.h
//  TableView
//
//  Created by exo on 2/15/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "RowDetailViewController.h"

@interface MyTableViewController : UITableViewController <RowDetailViewControllerDelegate>

@property (nonatomic, retain) NSArray *tableDataList;

@end
