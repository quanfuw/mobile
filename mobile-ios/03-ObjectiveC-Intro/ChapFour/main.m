//
//  main.m
//  ChapFour
//
//  Created by exo on 2/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "AppDelegate.h"
#import "Simple.h"
#import "NSString+FooCategory.h"

int main(int argc, char *argv[])
{
    NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
    Simple *simple = [[[Simple alloc] init] autorelease];
    [simple startPlay:@"Lion king" audienceNumbers:500 supportingActor:@"Lion" extrasNeeded:100];
    // test property
    simple.simpleName = @"Test property";
    NSLog(@"property value: %@", simple.simpleName);
    // test protocol
    [simple sayHello:@"Quang"];
    // test category
    NSString* string = [[[NSString alloc] init] autorelease];
    [string myMethod];
    int resVal = UIApplicationMain(argc, argv, nil, NSStringFromClass([AppDelegate class]));
    [pool release];
    return resVal;
}
