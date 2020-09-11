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

interface EdgeListProps {
    size: number;    // size of the grid
    onChange(edges: any): void;  // called when a new edge list is ready
}

/**
 * A Textfield that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps> {

    input:string = '';  // holds the string in the edge textarea

    onInputChange = (event: any) => {
        this.input = event.target.value;
    };

    onDrawClick = () => {
        // creates an array separating each line in the edges textarea
        let edges: Array<string> = this.input.split('\n');
        let finArray: Array<string> = [];
        let error: boolean = false;
        // goes through each value in the edges array
        edges.forEach(function (value) {
            let oneArray: Array<string> = [];
            let partArray = value.split(' ');
            // puts together an array that holds each value in a separate spot, including each x and y value and color
            partArray.forEach(function (value) {
                oneArray = oneArray.concat(value.split(','));
            });
            let i: any;
            // goes through the array and makes sure that each x and y value are actually numbers
            // and that there are no extra strings after the color. creates a message window if there is a problem.
            for(i in oneArray) {
                let part: string = oneArray[i];
                if(i<4){
                    let coordinate:number | undefined = parseInt(part);
                    // ensures all the x and y values are numbers
                    if(isNaN(coordinate) || coordinate === undefined) {
                        if(error === false) {
                            window.alert("There was an error with your edge values.\nThe right format is: x1,y1 x2,y2 color");
                        }
                        error = true;
                    }
                // ensures there are no extra strings in the edges textarea
                } else if (i > 4) {
                    if(error === false) {
                        window.alert("There was an error with your edge values.\nThe right format is: x1,y1 x2,y2 color");
                    }
                    error = true;
                }
            }
            // ensures that a color is included
            if(oneArray[4] === undefined) {
                if(error === false) {
                    window.alert("There was an error with your edge values.\nThe right format is: x1,y1 x2,y2 color");
                }
                error = true;
            }
            // adds it to the final edges array
            finArray = finArray.concat(oneArray);
        });
        // if no errors occurred tell parent component about the changes made to the array of edges
        if(error === false) {
            this.props.onChange(finArray);
        }
    };

    // clears the edges array and tells the parent component about the empty array,
    // but doesn't actually clear the textarea
    onClearClick = () => {
        this.props.onChange([]);
    };

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={this.onInputChange}
                /> <br/>
                <button onClick={this.onDrawClick}>Draw</button>
                <button onClick={this.onClearClick}>Clear</button>
            </div>
        );
    }

}

export default EdgeList;