<template>

<div id="simulator">
   
            <div id="toolbar">
            <button @click="start_simulation()" class="tool" id="start">Start Simulation <i class="fa-solid fa-play"></i> </button>
            <button @click="replay()" class="tool" id="replay">Replay <i class="fa-solid fa-reply"></i></button>
            <button @click="drawqueue()" class="tool" id="replay"> add queue</button>
            <button @click="drawmachine()" class="tool" id="replay">add machine</button>
            <button @click="drawlink()" class="tool" id="replay">add link</button>  
            </div>

    
<div id="platform">

 <v-stage :config="configKonva" ref="stage" @click="drawpart">
    <v-layer>

     <v-line
        v-for="(line, index) in lines"
        :key="index"
        :config="{
          x: 0,
          y: 0,
          points: line.points,
          stroke: 'black',
          strokeWidth: 4,
          draggable: false,
            lineCap: 'round',
           lineJoin: 'round',

        }"
      ></v-line> 
 

      <v-rect
        v-for="(queue, index) in queues"
        :key="index"
        :config="{
          x: queue.x,
          y: queue.y,
          width: 70,
          height: 60,
          fill: queue.fill, 
          stroke: 'black', 
          strokeWidth: 2,
          draggable: false,
          id: queue.id,
        }"
        @click="shapeClicked('queue', index)" ></v-rect>

      <v-circle
        v-for="(machine, index) in machines"
        :key="index"
        :config="{
          x: machine.x,
          y: machine.y,
          radius: 50,
          fill: machine.fill,
          stroke: 'black',
          strokeWidth: 2,
          draggable: false,
          id: machine.id,
        }"
     @click="shapeClicked('machine', index)">
      </v-circle>
      
       

     

       <v-text
         v-for="(txt,index) in texts"
         :key="index"
         :config="{
            x:txt.x,
            y:txt.y,
            id:txt.id,
            text:txt.text,
         }"
      >

      </v-text>
  



    </v-layer>
  </v-stage>
</div> 

  
</div>
</template>
<script>
import { ref } from "vue"

export default  {
  name:'App',
  data() {
    
    return {
      value: null,
      intervalId: null,
      configKonva: {
          width: window.innerWidth,
          height: 800,
      },
      isdraw:false,
      lin:false,
      circ:false,
      rectangle:false,
      currentShape:null,
      currenttext:null,
      graph:[],
      machineid:0,
      queueid:-1,
      queues:[],
      machines:[],
      lines:[],
      shapes:[],
      texts:[],
      fetched: [],
    };
  },
  methods: {
    drawpart()
    {
       if(!this.lin&&this.isdraw)
       {
          console.log(this.circ);
             if(this.circ)
         { 
                this.machineid++;
                const stage = this.$refs.stage.getStage();
                 console.log(stage);
                if (stage) {
                  const position = stage.getPointerPosition();
                 
                  if (position) {
                    this.currentShape = {
                      x: position.x,
                      y: position.y,
                      fill:"rgb(128,128,128)",
                      strokeWidth:2,
                      radius: 50,
                      id:"m"+String(this.machineid)
                    };
                    this.currenttext={
                       x:position.x-10,
                       y:position.y-10,
                       id:this.currentShape.id,
                       text: "m" + String(this.machineid)  // Corrected here
                    };
                  }
                }
         }
         else if(this.rectangle)
         {
                this.queueid++;
                const stage = this.$refs.stage.getStage();
                if (stage) {
                  const position = stage.getPointerPosition();
                  if (position) {
                    this.currentShape = {
                      x: position.x,
                      y: position.y,
                      fill:"#FFFF00",
                      strokeWidth:2,
                      width: 70,
                      height: 60,
                      id: "q" + String(this.queueid) // Corrected here
                    };
                    this.currenttext={
                       x:position.x+30,
                       y:position.y+20,
                       id:this.currentShape.id,
                       text: "q" + String(this.queueid) // Corrected here
                    };
                  }
                }
         }
         this.stopDrawing();
       }

       
    },
    
    stopDrawing() {
      
     
          if (this.isdraw) {
        this.isdraw = false;
        console.log("xxxxx");
        if(this.circ)
        {
          console.log("done");
          this.shapeType = 'Circle';
          console.log(this.currentShape);
          this.machines.push({ ...this.currentShape });
          this.texts.push({...this.currenttext})
          this.shapes.push({...this.currentShape});
                 
        }
      
      else if(this.rectangle){
        this.shapeType = 'Rectangle';

          this.queues.push({...this.currentShape});
           this.texts.push({...this.currenttext})
           console.log(this.currenttext);
            this.shapes.push({...this.currentShape});
      }
      else if(this.lin)
      {
        this.shapeType = 'Line';
        this.lines.push({...this.currentShape});
        this.shapes.push({...this.currentShape});
      }
        this.currentShape = null;
        this.currenttext=null;
        this.rectangle=false;
        this.circ=false;
      }
    },
  drawqueue()
{
  this.isdraw=true;
  this.rectangle=true;
  this.circ=false;
  this.lin=false;
},

drawmachine()
{
   this.isdraw=true;
  this.circ=true;
  this.rect=false;
  this.lin=false;
},
drawlink()
{
    this.isdraw=true;
  this.circ=false;
  this.rect=false;
  this.lin=true;
  this.edge={
    source:null,
    destination:null
  };
  this.currentShape={
    points:[]
  };
},
shapeClicked(type, index) {
    if (this.isdraw&&this.lin) {
        if (type === "queue") {
          console.log(this.edge.source !== null);
            if (this.edge.source !== null) {
                this.edge.destination = this.queues[index].id;
                this.currentShape.points[2] = this.queues[index].x+35;
                this.currentShape.points[3] = this.queues[index].y+30;
                this.isdraw = false;
                if(this.edge.source!==this.edge.destination)
                {
                       this.graph.push({ ...this.edge });      
                }     
                this.lines.push({ ...this.currentShape });
                this.edge = { source: null, destination: null }; // Reset edge
                this.currentShape = null;
            } else {
                this.edge.source = this.queues[index].id;
                this.currentShape.points[0] = this.queues[index].x+35;
                this.currentShape.points[1] = this.queues[index].y+30;
                console.log(this.edge);
            }
        } else if (type === "machine") {
            if (this.edge.source !== null) {
                this.edge.destination = this.machines[index].id;
                this.currentShape.points[2] = this.machines[index].x;
                this.currentShape.points[3] = this.machines[index].y;
                this.isdraw = false;
                if(this.edge.source!==this.edge.destination)
                {
                       this.graph.push({ ...this.edge });      
                }     
                this.lines.push({ ...this.currentShape });
                this.edge = { source: null, destination: null }; // Reset edge
                this.currentShape = null;
            } else {
                this.edge.source = this.machines[index].id;
                this.currentShape.points[0] = this.machines[index].x;
                this.currentShape.points[1] = this.machines[index].y;
            }
        }
        console.log(this.graph);
    }
},
// async replay()
// {
//     //  fetch of replay
//     this.startFetching(false);
    
// },
async start_simulation()
{
let backend = [];
let obj = null;

for (let i = 0; i < this.graph.length; i++) {
    let x = this.graph[i].source;
    obj = {
        source: x,
        destination: []
    };
      let v=0;
    for (let j = 0; j < this.graph.length; j++) {
        if (this.graph[j].source === x) {
            obj.destination[v]=this.graph[j].destination;
            v++;
        }
    }

    backend.push({ ...obj });
}
const response = await fetch("http://localhost:8080/api/start", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(backend)
        });

        if (!response.ok) {
            throw new Error('Failed to start simulation');
        }
      this.startFetching(true);
    
},
async fetchData() {
  ///please i want object on  form EX ----> {name:m0, color:green }
  //mapping of simulation in back
  //update 
  await fetch("http://localhost:8080/update",{
          method:"GET",
      }).then(res=>res.json)
        .then(data=>this.fetched=data)
        for (let i = 0; i < this.fetched.length; i++) 
            {
              const fetchedItem = this.fetched[i];
              if(fetchedItem.name[0]=='m')
                    {
                      // Update shapes array 
                      const shapeIndex = this.shapes.findIndex(shape => shape.id === fetchedItem.name);
                      if (shapeIndex !== -1) {
                        this.shapes[shapeIndex].fill=fetchedItem.color
                      }
                      // Update machines array 
                      const machineIndex = this.machines.findIndex(machine => machine.id === fetchedItem.name);
                      if (machineIndex !== -1) {
                        this.machines[machineIndex].fill=fetchedItem.color
                      }
                    }
                    else
                    {
                      const textIndex = this.texts.findIndex(text => text.id === fetchedItem.name);
                        if (textIndex !== -1) {
                          this.texts[textIndex].text = fetchedItem.color;
                        }
                    }
                  }
    },
async  fetchreplayData(){
      //mapping of replay in back
  await fetch("http://localhost:8080/    ",{
          method:"GET",
      }).then(res=>res.json)
        .then(data=>this.fetched=data)
        for (let i = 0; i < this.fetched.length; i++) 
            {
              const fetchedItem = this.fetched[i];
              if(fetchedItem.name[0]=='m')
                    {
                      // Update shapes array 
                      const shapeIndex = this.shapes.findIndex(shape => shape.id === fetchedItem.name);
                      if (shapeIndex !== -1) {
                        this.shapes[shapeIndex].fill=fetchedItem.color
                      }
                      // Update machines array 
                      const machineIndex = this.machines.findIndex(machine => machine.id === fetchedItem.name);
                      if (machineIndex !== -1) {
                        this.machines[machineIndex].fill=fetchedItem.color
                      }
                    }
                    else
                    {
                      const textIndex = this.texts.findIndex(text => text.id === fetchedItem.name);
                        if (textIndex !== -1) {
                          this.texts[textIndex].text = fetchedItem.color;
                        }
                    }
            }

    },
    startFetching(bool) {
      clearInterval(this.intervalId);
      if(bool==true)
          this.intervalId = setInterval(this.fetchData, 500);
      else
      this.intervalId = setInterval(this.fetchreplayData, 500);
    },
  },
 
};
</script>

<style>
@import "~@fortawesome/fontawesome-free/css/all.min.css";
@keyframes rotateAnimation {
    from {
        transform: rotate(0deg);
    }
    to {
        transform: rotate(360deg);
    }
}

@-webkit-keyframes pulse {
    0% { -webkit-transform: scale(1); }
    50% { -webkit-transform: scale(1.1); }
    100% { -webkit-transform: scale(1); }
}
@keyframes pulse {
    0% { transform: scale(1); }
    50% { transform: scale(1.1); }
    100% { transform: scale(1); }
}

#simulator
{
  height: 1000px;
  background-image: url(https://images.freecreatives.com/wp-content/uploads/2016/04/Website-Backgrounds-For-Desktop.jpg);
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center center;
}


#toolbar {
  width: 70%;
  margin-left: 15%;
  margin-right: 15%;
  display: flex;
   border: 2px solid black;
  justify-content: space-around; 
  border-radius: 15px; 
  overflow: hidden; 
  background: white;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  background-color: floralwhite;
}

.tool {
  width:20%;
  border: solid;
  padding: 10px;
  background-color: #3498db;
  color: white;
  font-size: 16px;
  cursor: pointer;
  border-radius: 15px; 
  transition: background-color 0.3s;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}
#confirm:hover
{
   opacity: 70%;
   animation: pulse 1.5s ;
   color:red
}
#start:hover
{
   opacity: 70%;
   animation: pulse 1.5s ;
   color: red;
}
#replay:hover
{
    opacity: 70%;
   animation: pulse 1.5s ;
   color:red
}
#platform
{
  margin-top: 20px;
  border: 2px solid black;
  background-color: white;
   width: 100%;
  
  margin-top: 3%;
  height:600px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}
.tool:hover {
  background-color: #2980b9;
}

</style>
