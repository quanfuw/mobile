//
//  main.m
//  MemManagement
//
//  Created by exo on 2/6/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "AppDelegate.h"
#import "MemManPolicy.h"
#import "PracticalMemMan.h"
#import "TestAutoreleasePool.h"


int main(int argc, char *argv[])
{
    // Memory management policy
    MemManPolicy *memManPol = [[[MemManPolicy alloc] init] autorelease];
    [memManPol rule];
    
    // Practical memory management
    PracticalMemMan *pracMemMan = [[[PracticalMemMan alloc] init] autorelease];
    [pracMemMan practical];
    
    // Autorelease Pool
    TestAutoreleasePool *arp = [[[TestAutoreleasePool alloc] init] autorelease];
    [arp testAutoRelease];
    
    @autoreleasepool {
        return UIApplicationMain(argc, argv, nil, NSStringFromClass([AppDelegate class]));
    }
}
