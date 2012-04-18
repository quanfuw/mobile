//
//  AppDelegate.h
//  NavInTab
//
//  Created by exo on 2/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate, UITabBarControllerDelegate>

@property (retain, nonatomic) IBOutlet UIWindow *window;
@property (retain, nonatomic) IBOutlet UITabBarController *tabBarController;
@property (retain, nonatomic) IBOutlet UINavigationController *navController;

@end
