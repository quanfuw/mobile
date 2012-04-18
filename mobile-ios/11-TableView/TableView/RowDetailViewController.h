//
//  RowDetailViewController.h
//  TableView
//
//  Created by exo on 2/15/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class RowDetailViewController;

@protocol RowDetailViewControllerDelegate 

- (void)viewRowDetailDidFinish:(RowDetailViewController*)controller;

@end

@interface RowDetailViewController : UIViewController

@property (assign, nonatomic) id <RowDetailViewControllerDelegate> delegate;
@property (retain, nonatomic) IBOutlet UILabel *detail;
- (IBAction)done:(id)sender;

@end
