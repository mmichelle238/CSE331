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
import Map from "./Map";

interface Point {
    x: number; //x value of the point
    y: number;  //y value of the point
}

interface AppState {
    src : string;   //start of path
    dest : string;  //end of path
    buildingList : any[];    //holds the name of all of the buildings
    pathPoints : Array<Point>; //hold the path values
}

class App extends Component<{}, AppState> {

    constructor(props: any) {
        super(props);
        this.state = {
            src: "PAR",
            dest: "PAR",
            buildingList: [],
            pathPoints: [],
        };
    }

    //gets the list of buildings from the server
    getBuildings() {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:4567/buildingNames");
        xhr.onload = () => this.buildingFinished(xhr);
        xhr.send(null);
    }

    // Called when we get the building list from the server and parses through the information
    //and updates the building list
    buildingFinished(xhr: XMLHttpRequest) {
        if (xhr.status === 200) {
            let options : any[] = [];
            if (xhr.responseText.length > 0) {
                let campusBuildings = JSON.parse(xhr.response);
                options.push(<option key={0} defaultValue={campusBuildings[0]}>{campusBuildings[0]}</option>);
                for (let i = 1; i < campusBuildings.length; i++) {
                    options.push(<option key={i}>{campusBuildings[i]}</option>);
                }
            }
            this.setState({buildingList:options});
        } else {
            // shows the user if there is an error
            window.alert("Error: " + xhr.statusText);
        }
    }

    componentDidMount() {
        this.getBuildings();
    }

    // Called when the user clicks on the find path button
    //gets a list of points representing a path from the server
    getPath = (evt:any) => {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:4567/findPath?src=" + this.state.src + "&dest="+this.state.dest);
        xhr.onload = () => this.pathFinished(xhr);
        xhr.send(null);
    };

    // Called when we get the path list from the server and parses through the information
    //and updates the array of points to hold the current points of the path
    pathFinished(xhr: XMLHttpRequest) {
        if (xhr.status === 200) {
            if (xhr.responseText.length > 0) {
                let path = JSON.parse(xhr.response);
                this.setState({pathPoints:path});
            }
        } else {
            // shows the user if there is an error
            window.alert("Error: " + xhr.statusText);
        }
    }

    //called when a new value is chosen in the dropdown menu
    //and updates the src string
    updateSrc = (evt:any) => {
        let fullName : string = evt.target.value;
        let startIndex : number =  fullName.indexOf("{");
        let shortName : string = fullName.substring(startIndex+1,fullName.length-1);
        this.setState({src:shortName});
    };

    //called when a new value is chosen in the dropdown meny
    //and updates the dest string
    updateDest = (evt:any) => {
        let fullName : string = evt.target.value;
        let startIndex : number =  fullName.indexOf("{");
        let shortName : string = fullName.substring(startIndex+1,fullName.length-1);
        this.setState({dest:shortName});
    };

    //called when the user clicks on the reset button
    //sets all of the states to their original values so the screen seems like it was just loaded
    resetApp = (evt:any) => {
        this.setState({
            src: "PAR",
            dest: "PAR",
            buildingList: [],
            pathPoints: [],
        });
        this.getBuildings();
    };

    render() {
        return (
            <div>
            <p>Find the shortest path between places around UW!</p>
            <p> Starting Point: <select name="src" onChange={this.updateSrc}>
                {this.state.buildingList}
            </select>
            </p>
            <p> Ending Point: <select name="dest" onChange={this.updateDest}>
                {this.state.buildingList}
            </select>
            </p>
            <button onClick={this.getPath}>Find Path</button>
            <button onClick={this.resetApp}>Reset</button>
            <p></p>
            <Map path={this.state.pathPoints}/>
            </div>
        );
    }

}

export default App;