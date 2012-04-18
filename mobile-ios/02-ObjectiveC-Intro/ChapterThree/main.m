//
//  main.m
//  ChapterThree
//
//  Created by exo on 2/2/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "AppDelegate.h"
#import "Simple.h"

int main(int argc, char *argv[])
{
    NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
    Simple * mySimple = [[Simple alloc] init];
    NSLog(@"retainCount: %d", [mySimple retainCount]);
    [mySimple sayHello:@"James"];
    [mySimple sayHelloTom];
    [mySimple release];
    [Simple sayGoodBye];
    int retVal = UIApplicationMain(argc, argv, nil, NSStringFromClass([AppDelegate class]));
    [pool release];
    return retVal;
    
}
