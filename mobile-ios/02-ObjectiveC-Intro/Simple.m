//
//  Simple.m
//  ChapThree
//
//  Created by exo on 2/2/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Simple.h"

@implementation Simple

+ (void) sayGoodBye {
    NSLog(@"Goodbye ...");
}

- (id)init {
    if (self = [super init]) {
        ;
    }
    return self;
}

- (id)initWithInt:(int)value {
    if (self = [super init]) {
        ;
    }
    return self;    
}

- (void)dealloc {
    NSLog(@"Deallocating Simple ...");
    [super dealloc];
}

- (id)retain {
    NSLog(@"Retaining Simple ...");
    return [super retain];
}

- (void)release {
    NSLog(@"Releasing Simple ...");
    [super release];
}

+ (id)alloc {
    NSLog(@"allocating Simple ...");
    return [super alloc];
}

- (void) sayHello:(NSString *)name {
    NSMutableString *message = [[NSMutableString alloc] initWithString:@"Hello there "];
    [message appendString:name];
    NSLog(message);
    [message release];
}

- (void) sayHelloTom {
    NSAutoreleasePool *releasePool = [[NSAutoreleasePool alloc] init];
    [self privateMethod];
    Simple *objSimple = [[[Simple alloc] autorelease] init];
    [objSimple sayHello:@"Tom"];
    [releasePool release];
}

- (void) privateMethod {
    NSLog(@"private method ...");
}

@end
