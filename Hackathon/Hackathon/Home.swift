//
//  Home.swift
//  Hackathon
//
//  Created by Hanbo Yu on 2021-01-16.
//

import SwiftUI

struct Home: View {
    var body: some View {
        VStack(spacing:40){
            HStack{
                Image("livecap")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width:100,height:100)
                Text("LiveCap")
                    .font(.largeTitle)
                    .padding(.trailing, 10.0)
                    .frame(width: 150.0, height: 100.0)
            }
            Text("LiveCap Provides real-time visual\n descriptors of photos and videos")
            Text("Choose to upload an existing\n photo/video or get captioning live\n while filming")
            HStack(spacing:70){
                Button(action:{
                    print("Test successfully")
                }){
                    VStack{
                        Image("upload")
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(width:100,height:100)
                        Text("Upload a picture").bold()
                    }
                }
                Button(action:{
                    print("Test successfully")
                }){
                    VStack{
                        Image("takepic")
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(width:100,height:100)
                        Text("Or take one now").bold()
                    }
                }
            }
            Button(action:{
                print("Test successfully")
            },label:{
                homebutton(buttonText: "Caption Settings", buttonColor:Color("settings"))
            })
            Button(action:{
                print("Test successfully")
            },label:{
                homebutton(buttonText: "How does this work?", buttonColor:Color.red)
            })
            
        }
    }
}

struct homebutton: View{
    var buttonText = "Sample"
    var buttonColor = Color.red
    var body: some View{
        ZStack{
        RoundedRectangle(cornerRadius: 5)
            .frame(width:250, height: 50)
            .foregroundColor(Color.gray)
            Text(buttonText).bold()
                .foregroundColor(.black)
            buttonshape()
                .trim(from: 0.41, to: 0.59)
                .fill(buttonColor)
                .frame(width:250,height:50)
        }
    }
    
    
}

struct buttonshape: Shape{
    func path(in rect: CGRect) -> Path {
        var path = Path()
        path.addRoundedRect(in: rect, cornerSize: CGSize(width:5,height:5))
        return path
    }
}

struct Home_Previews: PreviewProvider {
    static var previews: some View {
        Home()
    }
}

//struct homebutton_Previews: PreviewProvider {
//    static var previews: some View {
//        homebutton()
//    }
//}

