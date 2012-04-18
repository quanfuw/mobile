//
//  AImagePickerController.h
//  DatePicker
//
//  Created by exo on 2/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AImagePickerController : UIViewController <UINavigationControllerDelegate, UIImagePickerControllerDelegate>

@property (nonatomic, retain) UIImage *selectedImage;
@property (retain, nonatomic) IBOutlet UIImageView *theImageView;
@property (retain, nonatomic) IBOutlet UIBarButtonItem *takePhotoButton;
- (IBAction)selectPicture:(id)sender;
- (IBAction)takePhoto:(id)sender;
- (void)changeImage;

@end
