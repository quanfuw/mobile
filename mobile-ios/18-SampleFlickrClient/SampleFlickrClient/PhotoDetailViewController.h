//
//  PhotoDetailViewController.h
//  SampleFlickrClient
//
//  Created by exo on 2/24/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface PhotoDetailViewController : UIViewController <UIScrollViewDelegate>

@property (nonatomic, retain) NSDictionary *photo;
@property (retain, nonatomic) IBOutlet UIImageView *imageView;
@property (retain, nonatomic) IBOutlet UIScrollView *scrollView;


- (void)displayImage:(UIImage*)image;

@end
