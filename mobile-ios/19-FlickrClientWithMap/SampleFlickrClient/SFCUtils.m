//
//  SFCUtils.m
//  SampleFlickrClient
//
//  Created by exo on 2/27/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "SFCUtils.h"

@implementation SFCUtils

#pragma mark - utilities 

+ (NSString *)getDescriptionOfPhoto:(NSDictionary *)photo {
    return [[photo objectForKey:@"description"] objectForKey:@"_content"];
}

+ (NSString*)getTitleOfPhoto:(NSDictionary *)photo {
    NSString *title = [photo objectForKey:@"title"];
    NSString *description = [SFCUtils getDescriptionOfPhoto:photo];
    if (title == nil) {
        if (description && [description length] == 0) 
            title = [NSString stringWithString:description];
        else title = @"Unknown";
    }
    return title;
}

+ (NSString *)getCityNameOfPlace:(NSDictionary *)place {
    NSString *location = [SFCUtils getLocationOfPlace:place];
    return [location substringToIndex:[location rangeOfString:@","].location];
}

+ (NSString *)getLocationOfPlace:(NSDictionary *)place {
    return [place objectForKey:@"_content"];
}

@end
