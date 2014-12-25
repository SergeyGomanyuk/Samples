//
//  XYZPerson.m
//  LanguageLearning
//
//  Created by Sergey Gomanyuk on 08.10.14.
//  Copyright (c) 2014 sergg. All rights reserved.
//

#import "XYZPerson.h"

@implementation XYZPerson

+ (XYZPerson *)person {
    return [[self alloc] init];
}

+ (XYZPerson *)personWithFirstName:(NSString*)aFirstName lastName:(NSString*)aLastName {
    return [[self alloc] initWithFirstName:aFirstName lastName:aLastName];
}

+ (XYZPerson *)personWithFirstName:(NSString*)aFirstName lastName:(NSString*)aLastName birthDate:(NSDate*)aDOB {
    return [[self alloc] initWithFirstName:aFirstName lastName:aLastName birthDate:aDOB];
}

- (id)init {
    return [self initWithFirstName:@"John" lastName:@"Smith" birthDate:nil];
}

- (id)initWithFirstName:(NSString*)aFirstName lastName:(NSString*)aLastName {
    return [self initWithFirstName:aFirstName lastName:aLastName birthDate:nil];
}

- (id)initWithFirstName:(NSString*)aFirstName lastName:(NSString*)aLastName birthDate:(NSDate*)aDOB {
    self = [super init];
    if(self) {
        _firstName = [aFirstName copy];
        _lastName = aLastName;
        _birthDate = aDOB;
    }
    return self;
}


- (void)sayHello {
    NSString* str = [NSString stringWithFormat:@"Hello, %@ %@!", [self firstName], [self lastName] ];
    [self saySomething:str];
}

- (void)sayGoodbye {
    [self saySomething:@"Bye, World!"];
}

- (void)saySomething: (NSString*)something {
    NSLog(@"%@", something);
}

- (void)dealloc {
    NSLog(@"XYZPerson is being deallocated");
}



@end

