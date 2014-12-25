//
//  main.m
//  LanguageLearning
//
//  Created by Sergey Gomanyuk on 08.10.14.
//  Copyright (c) 2014 sergg. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "XYZPerson.h"
#import "XYZShoutingPerson.h"

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        // insert code here...
        // NSLog(@"Hello, World!");
        XYZPerson* person = [[XYZPerson alloc] init];
        [person sayHello];
        [person sayGoodbye];
        
        XYZPerson* shoutingPerson = [XYZShoutingPerson person];
        [shoutingPerson sayHello];
        [shoutingPerson sayGoodbye];
        
        XYZPerson* nilPerson;
        
        if(!nilPerson) {
            NSLog(@"nilPerson = nil");
        }
        
        NSMutableString* name = [NSMutableString stringWithString:@"Nick"];
        XYZPerson* nickPerson = [XYZPerson personWithFirstName:name lastName:@"Sinckevich"];
        [nickPerson sayHello];
        [name appendString:@"olay"];
        [nickPerson sayHello];
        
        NSLog(@"Start of checking deallocation");
        NSLog(@"Create strong person");
        XYZPerson* strongPerson = [XYZPerson person];
        NSLog(@"Create child person");
        XYZPerson* childPerson = [XYZPerson person];
        strongPerson.child = childPerson;
        childPerson.parent = strongPerson;
        NSLog(@"Remeber weak person");
        XYZPerson* __weak weakPerson = strongPerson;
        NSLog(@"Release child person");
        childPerson = nil;
        NSLog(@"Release strong person");
        strongPerson = nil;
        NSLog(@"Finish of checking deallocation");
        
    }
    return 0;
}
