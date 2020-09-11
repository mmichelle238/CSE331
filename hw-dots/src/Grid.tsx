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

interface GridProps {
    edges: Array<string>;   // an array holding all of the edges
    size: number;    // size of the grid to display
    width: number;   // width of the canvas on which to draw
    height: number;  // height of the canvas on which to draw
}

interface GridState {
    backgroundImage: any,  // image object rendered into the canvas (once loaded)
}

/**
 *  A simple grid with a variable size
 *
 *  Most of the assignment involves changes to this class
 */
class Grid extends Component<GridProps, GridState> {

    gridSizeSmall: boolean = false;

    canvasReference: React.RefObject<HTMLCanvasElement>

    constructor(props: GridProps) {
        super(props);
        this.state = {
            backgroundImage: null  // An image object to render into the canvas.
        };
        this.canvasReference = React.createRef();
    }

    componentDidMount() {
        // Since we're saving the image in the state and re-using it any time we
        // redraw the canvas, we only need to load it once, when our component first mounts.
        this.fetchAndSaveImage();
        this.redraw();
    }

    componentDidUpdate() {
        this.redraw()
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        const background = new Image();
        background.onload = () => {
            const newState = {
                backgroundImage: background
            };
            this.setState(newState);
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./image.jpg";
    }

    redraw = () => {

        if(this.canvasReference.current === null) {
            throw new Error("Unable to access canvas.");
        }
        const ctx = this.canvasReference.current.getContext('2d');
        if (ctx === null) {
            throw new Error("Unable to create canvas drawing context.");
        }

        ctx.clearRect(0, 0, this.props.width, this.props.height);
        // Once the image is done loading, it'll be saved inside our state.
        // Otherwise, we can't draw the image, so skip it.
        if (this.state.backgroundImage !== null) {
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }
        // Draw all the dots.
        const coordinates = this.getCoordinates();
        for (let coordinate of coordinates) {
            this.drawCircle(ctx, coordinate);
        }
        //Draw all edges.
        let drawError: boolean = false;
        let gridSize: number = this.props.size;
        let gridSizeError:boolean = this.gridSizeSmall;
        this.props.edges.forEach(function (value) {
            let point: number = parseInt(value);
            if(!isNaN(point)) {
                if(point >= gridSize || isNaN(gridSize)) {
                    // Ensures that the error message is only showed
                    if(gridSizeError === false) {
                        window.alert("Cannot draw edges, grid size must be at least " + (gridSize+1));
                    }
                    gridSizeError = true;
                    drawError = true;
                }
            }
        });
        // Ensures that the error message is only showed
        if(drawError === false) {
            gridSizeError = false;
        }
        this.gridSizeSmall = gridSizeError;
        // Only draws edges if the grid size fits the edges
        if(gridSizeError === false) {
            this.drawLines(ctx, coordinates);
        }
    };

    /**
     * Returns an array of coordinate pairs that represent all the points where grid dots should
     * be drawn.
     */
    getCoordinates = (): [number, number][] => {
        var i:number = 0;
        var j:number = 0;
        var coordinate: [number,number][];
        coordinate = [[0,0]];
        coordinate.pop();
        if(this.props.size % 2 === 0){
            // creates coordinates if the size of the grid is even
            var val1:number = (this.props.size+1);
            var dist1:number = 500/(val1);
            for(i=0;i<this.props.size;i++){
                for(j=0; j<this.props.size;j++){
                    coordinate.push([(i*dist1)+dist1,(j*dist1)+dist1]);
                }
            }
            return coordinate;

        } else {
            // creates coordinates if the size of the grid is odd
            var val:number = ((this.props.size-1)/2);
            var dist:number = 250/(val+1);
            for(i=val*(-1);i<=val;i++){
                for(j=val*(-1); j<=val;j++){
                    coordinate.push([(i*dist)+250,(j*dist)+250]);
                }
            }
            return coordinate;
        }
    };

    // You could write CanvasRenderingContext2D as the type for ctx, if you wanted.
    drawCircle = (ctx: any , coordinate: [number, number]) => {
        ctx.fillStyle = "white";
        // Generally use a radius of 4, but when there are lots of dots on the grid (> 50)
        // we slowly scale the radius down so they'll all fit next to each other.
        const radius = Math.min(4, 100 / this.props.size);
        ctx.beginPath();
        ctx.arc(coordinate[0], coordinate[1], radius, 0, 2 * Math.PI);
        ctx.fill();
    };

    // Draws lines based off of the array of edges
    drawLines = (ctx:any , coordinates: [number, number][]) => {
        let i: any;
        let x1: number = 0;
        let x2: number = 0;
        let y1: number = 0;
        let y2: number = 0;
        for(i in this.props.edges) {
            // first sets up the actual coordinates of the points and then draws the lines
            if(i%5 === 0) {
                x1 = parseInt(this.props.edges[i]);
            } else if(i%5 === 1) {
                y1 = parseInt(this.props.edges[i]);
            } else if (i%5 === 2) {
                x2 = parseInt(this.props.edges[i]);
            } else if (i%5 === 3) {
                y2 = parseInt(this.props.edges[i]);
            } else {
                // draws the actual lines and changes the colors
                let color: string = this.props.edges[i];
                ctx.beginPath();
                ctx.strokeStyle = color;
                ctx.lineWidth = 3;
                let start = coordinates[(x1*this.props.size)+y1];
                let end = coordinates[(x2*this.props.size)+y2];
                ctx.moveTo(start[0],start[1]);
                ctx.lineTo(end[0],end[1]);
                ctx.stroke();
            }
        }
    }

    // if the grid size is empty, sets the size to 0
    checksNaN = () => {
        if(isNaN(this.props.size)) {
            return 0;
        }
        return this.props.size;
    }

    render() {
        return (
            <div id="grid">
                <canvas ref={this.canvasReference} width={this.props.width} height={this.props.height}/>
                <p>Current Grid Size: {this.checksNaN()} </p>
            </div>
        );
    }
}

export default Grid;