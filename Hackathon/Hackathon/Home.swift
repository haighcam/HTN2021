//
//  Home.swift
//  Hackathon
//
//  Created by Hanbo Yu on 2021-01-16.
//

import SwiftUI

struct Home: View {
    var body: some View {
        VStack(spacing:20){
            Text("LiveCap Provides real-time visual\n descriptors of photos and videos")
            Text("Choose to upload an existing\n photo/video or get captioning live\n while filming")
            Spacer()
            HStack{
                Text("Upload")
                Text("Open Camera")
            }
            
        }
    }
}

