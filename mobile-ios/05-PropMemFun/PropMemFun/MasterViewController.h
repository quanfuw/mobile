//
//  MasterViewController.h
//  PropMemFun
//
//  Created by exo on 2/7/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface MasterViewController : UITableViewController {
    NSArray *_sushiTypes;
    NSString *_lastSushiSelected;
}

@property (nonatomic, retain) NSArray *sushiTypes;
@property (nonatomic, retain) NSString *lastSushiSelected;

@end
