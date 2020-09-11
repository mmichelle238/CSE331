/*
 * Copyright (C) 2020 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import "./Map.css";

interface Point {
    x: number;  //x value of the point
    y: number;  //y value of the point
}

interface MapProps {
    path: Array<Point>; //hold the path values
}

interface MapState {
    backgroundImage: HTMLImageElement | null;
}

class Map extends Component<MapProps, MapState> {

    // NOTE:
    // This component is a suggestion for you to use, if you would like to.
    // It has some skeleton code that helps set up some of the more difficult parts
    // of getting <canvas> elements to display nicely with large images.
    //
    // If you don't want to use this component, you're free to delete it.

    canvas: React.RefObject<HTMLCanvasElement>;

    constructor(props: MapProps) {
        super(props);
        this.state = {
            backgroundImage: null
        };
        this.canvas = React.createRef();
    }

    componentDidMount() {
        this.fetchAndSaveImage();
        this.drawBackgroundImage();
    }

    componentDidUpdate() {
        this.drawBackgroundImage();
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background: HTMLImageElement = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }


    drawBackgroundImage() {
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let ctx = canvas.getContext("2d");
        if (ctx === null) throw Error("Unable to draw, no valid graphics context.");

        if (this.state.backgroundImage !== null) { // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry.
            canvas.width = this.state.backgroundImage.width;
            canvas.height = this.state.backgroundImage.height;
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }
        //draw lines for the path
        this.drawPath(ctx);
    }

    //draws the path based on the array of points
    drawPath = (ctx:any) => {
        let path: Array<Point> = this.props.path;
        let index: number = 0;
        let x1: number = 0;
        let x2: number = 0;
        let y1: number = 0;
        let y2: number = 0;
        let i: any;
        for(i in path) {
            if(index === 0) {
                x1 = path[i].x;
                y1 = path[i].y;
                index = 1;
            } else {
                x2 = path[i].x;
                y2 = path[i].y;
                index = 0;
                ctx.beginPath();
                ctx.strokeStyle = "blue";
                ctx.lineWidth = 7;
                ctx.moveTo(x1,y1);
                ctx.lineTo(x2,y2);
                ctx.stroke();
            }
        }
    }

    render() {
        return (
            <div id="map">
                <canvas ref={this.canvas}/>
            </div>
        );
    }
}

export default Map;