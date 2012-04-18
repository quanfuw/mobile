//
//  ScaryBugData.m
//  ScaryBugs
//
//  Created by exo on 2/21/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ScaryBugData.h"

@implementation ScaryBugData
@synthesize title = _title;
@synthesize rating = _rating;

- (void)dealloc {
    [_title release];
    [super dealloc];
}

- (id)initWithTitle:(NSString *)title rating:(float)rating {
    if (self = [super init]) {
        [_title release];
        [title retain];
        _title = title;
        _rating = rating;
    }
    return self;
}

@end
