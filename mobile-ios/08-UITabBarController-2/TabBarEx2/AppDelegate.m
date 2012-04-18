//
//  AppDelegate.m
//  TabBarEx2
//
//  Created by exo on 2/13/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "AppDelegate.h"
#import "UIFirstViewController.h"

@implementation AppDelegate

@synthesize window = _window;
@synthesize taskBarController = _taskBarController;

- (void)dealloc
{
    [_window release];
    [_taskBarController release];
    [super dealloc];
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]] autorelease];
    // Override point for customization after application launch.
    self.window.backgroundColor = [UIColor whiteColor];
    self.taskBarController = [[[UITabBarController alloc] initWithNibName:@"MyTabBarController" bundle:nil] autorelease];
    UIViewController *firstViewController = [[[UIFirstViewController alloc] initWithNibName:@"UIFirstViewController" bundle:nil] autorelease];
    NSArray *array = [NSArray arrayWithObject:firstViewController];
    [self.taskBarController setViewControllers:array animated:YES];
    self.taskBarController.customizableViewControllers = nil;
    self.window.rootViewController = self.taskBarController;
    self.taskBarController.delegate = self;
    [self.window makeKeyAndVisible];
    return YES;
}

- (BOOL)tabBarController:(UITabBarController *)tabBarController shouldSelectViewController:(UIViewController *)viewController {
    if ([viewController isEqual:[tabBarController.viewControllers objectAtIndex:1]]) {
        UIAlertView *alert = [[[UIAlertView alloc] initWithTitle:@"Message" message:@"You cannot select this tab!" delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil, nil] autorelease];
        [alert show];
        return NO;
    }
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
   
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
