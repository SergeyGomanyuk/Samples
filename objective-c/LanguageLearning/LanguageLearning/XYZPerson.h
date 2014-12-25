//
//  XYZPerson.h
//  LanguageLearning
//
//  Created by Sergey Gomanyuk on 08.10.14.
//  Copyright (c) 2014 sergg. All rights reserved.
//

#ifndef LanguageLearning_XYZPerson_h
#define LanguageLearning_XYZPerson_h

#import <Foundation/Foundation.h>

@interface XYZPerson : NSObject

@property (copy) NSString *firstName;
@property NSString *lastName;
@property NSDate *birthDate;

@property XYZPerson* child;
@property (weak) XYZPerson* parent;


+ (XYZPerson *)person;
+ (XYZPerson *)personWithFirstName:(NSString*)aFirstName lastName:(NSString*)aLastName;
+ (XYZPerson *)personWithFirstName:(NSString*)aFirstName lastName:(NSString*)aLastName birthDate:(NSDate*)aDOB;

- (id)initWithFirstName:(NSString*)aFirstName lastName:(NSString*)aLastName;
- (id)initWithFirstName:(NSString*)aFirstName lastName:(NSString*)aLastName birthDate:(NSDate*)aDOB;
- (void)sayHello;
- (void)sayGoodbye;
- (void)saySomething: (NSString*)something;



@end

#endif
