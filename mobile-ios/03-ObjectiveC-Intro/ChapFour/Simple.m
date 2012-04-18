//
//  Simple.m
//  ChapFour
//
//  Created by exo on 2/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Simple.h"

@implementation Simple
@synthesize simpleName;

- (void)startPlay:(NSString *)title audienceNumbers:(int)value supportingActor:(NSString *)actorValue extrasNeeded:(int)extrasvalue {
    NSLog(@"The title: %@", title);
    NSLog(@"Audience: %d", value);
    NSLog(@"Actor: %@", actorValue);
    NSLog(@"Extras needed: %d", extrasvalue);
}

- (void)sayHello:(NSString *)name {
    NSLog(@"Hello %@", name);
}
@end
