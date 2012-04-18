//
//  PracticalMemMan.m
//  MemManagement
//
//  Created by exo on 2/6/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PracticalMemMan.h"
#import "Counter.h"

@implementation PracticalMemMan

- (void)practical {
    Counter *counter = [[[Counter alloc] init] autorelease];
    counter.count = [[[NSNumber alloc] initWithInt:10] autorelease];
    NSLog(@"Number: %@", counter.count);
    [counter reset];
    NSLog(@"Number after reset: %@", counter.count);
    
    // Collections Own the Objects They Contain
    NSMutableArray *array = [[NSMutableArray alloc] initWithCapacity:10];
    for (int i = 0; i < 10; i++) {
        NSNumber *number = [[NSNumber alloc] initWithInt:i];
        [array addObject:number];
        // release object after add to array because the array owns it.
        [number release];
    }
    
    
}

@end
