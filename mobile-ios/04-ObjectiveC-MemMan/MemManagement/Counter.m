//
//  Counter.m
//  MemManagement
//
//  Created by exo on 2/6/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Counter.h"

@implementation Counter

- (id)init {
    if (self = [super init]) {
        // Don’t Use Accessor Methods in Initializer Methods and dealloc
        _count = [[NSNumber alloc] initWithInt:10];
    }
    return self;
}

- (id)initWithCount:(NSNumber *)startingCount {
    if (self = [super init]) {
        _count = [startingCount copy];
    }
    return self;
}

- (void)setCount:(NSNumber *)count {
    // take ownership of new object
    [count retain];
    // retain old object
    [_count release];
    // Make the new assignment
    _count = count;
}

- (NSNumber *)count {
    return _count;
}

- (void)reset {
    NSNumber *zero = [[NSNumber alloc] initWithInt:0];
    [self setCount:zero];
    [zero release];
}

- (void)dealloc {
    // Don’t Use dealloc to Manage Scarce Resources
    [_count release];
    [super dealloc];
}

@end
