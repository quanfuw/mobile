//
//  AutoreleaseTool.m
//  MemManagement
//
//  Created by exo on 2/6/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "TestAutoreleasePool.h"

@implementation TestAutoreleasePool
- (void)testAutoRelease {
    NSMutableArray *array = [[NSMutableArray alloc] initWithCapacity:1000];
    for (int i = 0; i < 1000; i++) {
        NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
        NSString *string = [[[NSString alloc] initWithFormat:@"String %d", i] autorelease];
        [array addObject:string];                
        [pool drain];
    }
    
    for (NSString *string in array) {
        NSLog(@"element: %@", string);
    }
}
@end
