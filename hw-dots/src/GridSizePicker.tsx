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

/* A simple TextField that only allows numerical input */

import React, {Component} from 'react';

interface GridSizePickerProps {
    value: string;                    // text to display in the text area
    onChange(newSize: number): void;  // called when a new size is picked
}

class GridSizePicker extends Component<GridSizePickerProps> {

    onInputChange = (event: any) => {
        // Every event handler with JS can optionally take a single parameter that
        // is an "event" object - contains information about an event. For mouse clicks,
        // it'll tell you thinks like what x/y coordinates the click was at. For text
        // box updates, it'll tell you the new contents of the text box, like we're using
        // below.
        //
        // We wrote "any" here because the type of this object is long and complex.
        // If you're curious, the exact type would be: React.ChangeEvent<HTMLInputElement>

        // ensures the size of grid is greater than 1 and less than 100
        // if it isn't create a message that informs the user of the size limits
        let newSize: number = parseInt(event.target.value);
        // if the size is greater than 100 remove the last number that was entered in
        if (newSize > 100 ) {
            newSize = Math.round((newSize/10)-0.5);
            window.alert("Size can't be bigger than 100");
        // if the size is less than 0 make it equal to 1
        } else if (newSize < 0) {
            newSize = 1;
            window.alert("Size can't be less than 1");
        }
        this.props.onChange(newSize); // Tell our parent component about the new size.
    };

    render() {
        return (
            <div id="grid-size-picker">
                <label>
                    Grid Size:
                    <input
                        value={this.props.value}
                        onChange={this.onInputChange}
                        type="number"
                        min={1}
                        max={100}
                    />
                </label>
            </div>
        );
    }
}

export default GridSizePicker;
