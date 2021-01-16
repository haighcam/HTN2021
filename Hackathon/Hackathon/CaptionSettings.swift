//
//  CaptionSettings.swift
//  Hackathon
//
//  Created by Hanbo Yu on 2021-01-16.
//

import SwiftUI

struct CaptionSettings: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 40){
            Text("Adjust Caption Display").bold()
            VStack (spacing:40)
            {
                VStack{
                Text("Text Size")
                HStack(spacing: 55){
                    Button(action:{
                        print("Test successfully")
                    }){
                        Text("Small").font(.system(size: 15))
                        }
                    Button(action:{
                        print("Test successfully")
                    }){
                        Text("Medium").font(.system(size: 20))
                        }
                    Button(action:{
                        print("Test successfully")
                    }){
                        Text("Large").font(.system(size: 25))
                        }
                }}
                VStack{
                Text("Text Colour")
                    HStack(spacing:32){
                    Button(action:{
                        print("Test successfully")
                    }){
                        Image("white")
                        }
                    Button(action:{
                        print("Test successfully")
                    }){
                        Image("black")
                        }
                    Button(action:{
                        print("Test successfully")
                    }){
                        Image("red")
                        }
                    Button(action:{
                        print("Test successfully")
                    }){
                        Image("blue")
                        }
                    }
                }
                VStack{
                    Text("Text Background")
                    HStack(spacing:32){
                        Button(action:{
                            print("Test successfully")
                        }){
                            Image("none")
                            }
                        Button(action:{
                            print("Test successfully")
                        }){
                            Image("white")
                            }
                        Button(action:{
                            print("Test successfully")
                        }){
                            Image("black")
                            }
                    }
                }
                HStack{
                    Button(action:{
                        print("Test successfully")
                    },label:{
                        settingsbutton(buttonText: "RETURN")
                    })
                    Button(action:{
                        print("Test successfully")
                    },label:{
                        settingsbutton(buttonText: "SAVE")
                    })
                }
            }
        }
    }
}

struct settingsbutton: View{
    var buttonText = "Sample"
    var buttonColor = Color.init(red: 46, green: 122, blue: 165)
    var body: some View{
        ZStack{
        RoundedRectangle(cornerRadius: 50)
            .frame(width:130, height: 50)
            Text(buttonText).bold()
                .foregroundColor(.black)
            buttonshape()
                .trim(from: 0.41, to: 0.59)
                .fill(buttonColor)
                .frame(width:130,height:50)
        }
    }
    
    
}

struct CaptionSettings_Previews: PreviewProvider {
    static var previews: some View {
        CaptionSettings()
    }
}
