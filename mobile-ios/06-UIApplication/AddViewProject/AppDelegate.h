//
//  AppDelegate.h
//  AddViewProject
//
//  Created by exo on 2/8/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FirstViewController.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate> {
    FirstViewController *_first;
}

@property (strong, nonatomic) UIWindow *window;
@property (strong, retain) FirstViewController *first;
@end
