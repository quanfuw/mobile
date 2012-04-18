//
//  AppDelegate.m
//  FailedBanksCD
//
//  Created by exo on 3/1/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "AppDelegate.h"
#import "FailedBankInfo.h"
#import "FailedBankDetails.h"
#import "FailedBanksListViewController.h"

@implementation AppDelegate

@synthesize window = _window;
@synthesize managedObjectContext = __managedObjectContext;
@synthesize managedObjectModel = __managedObjectModel;
@synthesize persistentStoreCoordinator = __persistentStoreCoordinator;

- (void)dealloc
{
    [_window release];
    [__managedObjectContext release];
    [__managedObjectModel release];
    [__persistentStoreCoordinator release];
    [super dealloc];
}

- (void)insertDataWithModel {
    NSManagedObjectContext *context = self.managedObjectContext;
    FailedBankInfo *failedBankInfo = [NSEntityDescription insertNewObjectForEntityForName:@"FailedBankInfo" inManagedObjectContext:context];
    failedBankInfo.name = @"Test bank 1";
    failedBankInfo.city = @"Testville 1";
    failedBankInfo.state = @"Testland 1";
    FailedBankDetails *failedBankDetails = [NSEntityDescription insertNewObjectForEntityForName:@"FailedBankDetails" inManagedObjectContext:context];
    failedBankDetails.closeDate = [NSDate date];
    failedBankDetails.updatedDate = [NSDate date];
    failedBankDetails.zip = [NSNumber numberWithInt:12345];
    failedBankDetails.info = failedBankInfo;
    failedBankInfo.details = failedBankDetails;
    NSError *error;
    if (![context save:&error]) {
        NSLog(@"Opps, couldn't save: %@", error.localizedDescription);
    }
    
    // test listing all FailedBankInfos from the scope 
    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
    NSEntityDescription *entity = [NSEntityDescription entityForName:@"FailedBankInfo" inManagedObjectContext:context];
    [fetchRequest setEntity:entity];
    NSArray *fetchedObjects = [context executeFetchRequest:fetchRequest error:&error];
    for (FailedBankInfo *info in fetchedObjects) {
        NSLog(@"Name: %@", info.name);
        FailedBankDetails *details = info.details;
        NSLog(@"Zip: %@", details.zip);
    }
    
    
    [fetchRequest release];
}

- (void)fetchData {
    NSManagedObjectContext *context = self.managedObjectContext;
    NSLog(@"Fetching request ...");
    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
    NSEntityDescription *entity = [NSEntityDescription 
                                   entityForName:@"FailedBankInfo" inManagedObjectContext:context];
    [fetchRequest setEntity:entity];
    NSError *error;
    NSArray *fetchedObjects = [context executeFetchRequest:fetchRequest error:&error];
    for (NSManagedObject *info in fetchedObjects) {
        NSLog(@"Name: %@", [info valueForKey:@"name"]);
        NSManagedObject *details = [info valueForKey:@"details"];
        NSLog(@"Zip: %@", [details valueForKey:@"zip"]);
    }        
    [fetchRequest release];
}

- (void)insertData {
    NSManagedObjectContext *context = self.managedObjectContext;
    NSManagedObject *failedBankInfo = [NSEntityDescription insertNewObjectForEntityForName:@"FailedBankInfo" inManagedObjectContext:context];
    [failedBankInfo setValue:@"Test bank" forKey:@"name"];
    [failedBankInfo setValue:@"Testville" forKey:@"city"];
    [failedBankInfo setValue:@"Testland" forKey:@"state"];
    
    NSManagedObject *failedBankDetail = [NSEntityDescription insertNewObjectForEntityForName:@"FailedBankDetails" inManagedObjectContext:context];
    [failedBankDetail setValue:[NSDate date] forKey:@"closeDate"];
    [failedBankDetail setValue:[NSDate date] forKey:@"updatedDate"];
    [failedBankDetail setValue:[NSNumber numberWithInt:12345] forKey:@"zip"];
    [failedBankDetail setValue:failedBankInfo forKey:@"info"];
    [failedBankInfo setValue:failedBankDetail forKey:@"details"];
    NSError *error;
    if (![context save:&error]) {
        NSLog(@"Opps, couldn't save: %@", error.localizedDescription);
    }
    
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]] autorelease];
    
    FailedBanksListViewController *failedBanksView = [[[FailedBanksListViewController alloc] initWithStyle:UITableViewStyleGrouped] autorelease];
    failedBanksView.context = self.managedObjectContext;
    UINavigationController *viewController = [[UINavigationController alloc] initWithRootViewController:failedBanksView];
    [self.window setRootViewController:viewController];
    self.window.backgroundColor = [UIColor whiteColor];
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
    // Saves changes in the application's managed object context before the application terminates.
    [self saveContext];
}

- (void)saveContext
{
    NSError *error = nil;
    NSManagedObjectContext *managedObjectContext = self.managedObjectContext;
    if (managedObjectContext != nil)
    {
        if ([managedObjectContext hasChanges] && ![managedObjectContext save:&error])
        {
            /*
             Replace this implementation with code to handle the error appropriately.
             
             abort() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development. 
             */
            NSLog(@"Unresolved error %@, %@", error, [error userInfo]);
            abort();
        } 
    }
}

#pragma mark - Core Data stack

/**
 Returns the managed object context for the application.
 If the context doesn't already exist, it is created and bound to the persistent store coordinator for the application.
 */
- (NSManagedObjectContext *)managedObjectContext
{
    if (__managedObjectContext != nil)
    {
        return __managedObjectContext;
    }
    
    NSPersistentStoreCoordinator *coordinator = [self persistentStoreCoordinator];
    if (coordinator != nil)
    {
        __managedObjectContext = [[NSManagedObjectContext alloc] init];
        [__managedObjectContext setPersistentStoreCoordinator:coordinator];
    }
    return __managedObjectContext;
}

/**
 Returns the managed object model for the application.
 If the model doesn't already exist, it is created from the application's model.
 */
- (NSManagedObjectModel *)managedObjectModel
{
    if (__managedObjectModel != nil)
    {
        return __managedObjectModel;
    }
    NSURL *modelURL = [[NSBundle mainBundle] URLForResource:@"FailedBanksCD" withExtension:@"momd"];
    __managedObjectModel = [[NSManagedObjectModel alloc] initWithContentsOfURL:modelURL];
    return __managedObjectModel;
}

/**
 Returns the persistent store coordinator for the application.
 If the coordinator doesn't already exist, it is created and the application's store added to it.
 */
- (NSPersistentStoreCoordinator *)persistentStoreCoordinator
{
    if (__persistentStoreCoordinator != nil)
    {
        return __persistentStoreCoordinator;
    }

    NSURL *storeURL = [[self applicationDocumentsDirectory] URLByAppendingPathComponent:@"FailedBanksCD.sqlite"];
    NSFileManager *fileManager = [NSFileManager defaultManager];
    if (![fileManager fileExistsAtPath:storeURL.path]) {
        NSString *defaultStorePath = [[NSBundle mainBundle] pathForResource:@"FailedBanksCD" ofType:@"sqlite"];
        if (defaultStorePath)
            [fileManager copyItemAtPath:defaultStorePath toPath:storeURL.path error:nil];
    }
    
    NSError *error = nil;
    __persistentStoreCoordinator = [[NSPersistentStoreCoordinator alloc] initWithManagedObjectModel:[self managedObjectModel]];
    if (![__persistentStoreCoordinator addPersistentStoreWithType:NSSQLiteStoreType configuration:nil URL:storeURL options:nil error:&error])
    {
        NSLog(@"Unresolved error %@, %@", error, [error userInfo]);
        abort();
    }    
    
    return __persistentStoreCoordinator;
}

#pragma mark - Application's Documents directory

/**
 Returns the URL to the application's Documents directory.
 */
- (NSURL *)applicationDocumentsDirectory
{
    return [[[NSFileManager defaultManager] URLsForDirectory:NSDocumentDirectory inDomains:NSUserDomainMask] lastObject];
}

@end
