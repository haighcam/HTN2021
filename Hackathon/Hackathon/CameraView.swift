//
//  CameraView.swift
//  Hackathon
//
//  Created by Hanbo Yu on 2021-01-16.
//

import SwiftUI

struct CameraView: View {
    var body: some View {
        ZStack{
            Color.black
                .ignoresSafeArea(.all, edges: .all)
            VStack{
                Spacer()
                HStack{
                    Button(action:{
                    },label:{
                        ZStack{
                        Circle()
                        .fill(Color.white)
                        }})
                }
            }
        }
        }
    }

struct CameraView_Previews: PreviewProvider {
    static var previews: some View {
        CameraView()
    }
}
