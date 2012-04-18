//
//  AppDelegate.m
//  SampleFlickrClient
//
//  Created by exo on 2/24/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "AppDelegate.h"
#import "PlacesViewController.h"
#import "PhotosViewController.h"
#import "CacheManager.h"
#import "VacationsTableViewController.h"

@implementation AppDelegate

@synthesize window = _window;
@synthesize viewController = _viewController;

- (void)dealloc
{
    [_window release];
    [_viewController release];
    [super dealloc];
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    [CacheManager declarePrefsIfNotExist];
    [CacheManager initCache:nil];
    self.window = [[[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]] autorelease];
    // Override point for customization after application launch.
    self.viewController = [[[UITabBarController alloc] init] autorelease];
    // init tab view controllers 
    UIViewController *placesController = [[[PlacesViewController alloc] initWithNibName:@"PlacesViewController" bundle:nil] autorelease];
    UINavigationController *controller1 = [[[UINavigationController alloc] initWithRootViewController:placesController] autorelease];
    controller1.tabBarItem.title = @"Places";
    controller1.tabBarItem.image = [UIImage imageNamed:@"city.png"];
    
    PhotosViewController *recentController = [[[PhotosViewController alloc] initWithNibName:@"PhotosViewController" bundle:nil] autorelease];
    recentController.recentsView = YES;
    UINavigationController *controller2 = [[[UINavigationController alloc] initWithRootViewController:recentController] autorelease];
    controller2.tabBarItem.title = @"Recents";
    controller2.tabBarItem.image = [UIImage imageNamed:@"clock.png"];
    
    VacationsTableViewController *vacationsController = [[[VacationsTableViewController alloc] initWithStyle:UITableViewStyleGrouped] autorelease];
    UINavigationController *controller3 = [[[UINavigationController alloc] initWithRootViewController:vacationsController] autorelease];
    controller3.tabBarItem.title = @"Vacations";
    controller3.tabBarItem.image = [UIImage imageNamed:@"calendar.png"];
    self.viewController.viewControllers = [NSArray arrayWithObjects:controller1, controller2, controller3, nil];
    self.window.rootViewController = self.viewController;
    [self.window makeKeyAndVisible];
    
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
     If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
     */
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    /*
     Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
     */
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    /*
     Called when the application is about to terminate.
     Save data if appropriate.
     See also applicationDidEnterBackground:.
     */
}

@end
