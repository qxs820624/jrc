/**
 * Adobe Edge: symbol definitions
 */
(function($, Edge, compId){
//images folder
var im='images/';

var fonts = {};


var resources = [
];
var symbols = {
"stage": {
   version: "1.5.0",
   minimumCompatibleVersion: "1.5.0",
   build: "1.5.0.217",
   baseState: "Base State",
   initialState: "Base State",
   gpuAccelerate: false,
   resizeInstances: false,
   content: {
         dom: [
         {
            id:'huajuan5',
            display:'none',
            type:'image',
            rect:['-82px','253px','486px','3px','auto','auto'],
            fill:["rgba(0,0,0,0)",im+"huajuan5.png",'0px','0px'],
            transform:[[],[],[],['0.5','0.5']]
         },
         {
            id:'huajuan2',
            display:'none',
            type:'image',
            rect:['-82px','231px','486px','48px','auto','auto'],
            fill:["rgba(0,0,0,0)",im+"huajuan2.png",'0px','0px'],
            transform:[[],[],[],['0.5','0.5']]
         },
         {
            id:'huajuan3',
            display:'none',
            type:'image',
            rect:['-82px','249px','486px','24px','auto','auto'],
            fill:["rgba(0,0,0,0)",im+"huajuan3.png",'0px','0px'],
            transform:[[],[],[],['0.5','0.5']]
         },
         {
            id:'huajuan1',
            type:'image',
            rect:['-82px','191px','486px','84px','auto','auto'],
            fill:["rgba(0,0,0,0)",im+"huajuan1.png",'0px','0px'],
            transform:[[],[],[],['0.183','0.183']]
         },
         {
            id:'huajuan4',
            type:'image',
            rect:['-82px','206px','486px','84px','auto','auto'],
            fill:["rgba(0,0,0,0)",im+"huajuan4.png",'0px','0px'],
            transform:[[],[],[],['0.183','0.183']]
         },
         {
            id:'symbol_content',
            type:'rect',
            rect:['64','73','auto','auto','auto','auto']
         }],
         symbolInstances: [
         {
            id:'symbol_content',
            symbolName:'symbol_content'
         }
         ]
      },
   states: {
      "Base State": {
         "${_symbol_content}": [
            ["transform", "scaleY", '1.15829'],
            ["style", "top", '58px']
         ],
         "${_huajuan4}": [
            ["style", "top", '135px'],
            ["transform", "scaleY", '0.18293'],
            ["transform", "scaleX", '0.18293'],
            ["style", "opacity", '0'],
            ["style", "left", '-82px']
         ],
         "${_huajuan5}": [
            ["style", "top", '182px'],
            ["transform", "scaleY", '0.5'],
            ["transform", "scaleX", '0.5'],
            ["style", "display", 'none'],
            ["style", "left", '-82px'],
            ["style", "height", '3px']
         ],
         "${_Stage}": [
            ["color", "background-color", 'rgba(0,0,0,0.00)'],
            ["style", "overflow", 'hidden'],
            ["style", "height", '327px'],
            ["gradient", "background-image", [270,[['rgba(0,0,0,0.00)',0],['rgba(255,255,255,0.00)',100]]]],
            ["style", "width", '320px']
         ],
         "${_huajuan2}": [
            ["style", "top", '162px'],
            ["transform", "scaleY", '0.5'],
            ["transform", "scaleX", '0.5'],
            ["style", "left", '-82px'],
            ["style", "display", 'none']
         ],
         "${_huajuan1}": [
            ["style", "top", '122px'],
            ["transform", "scaleY", '0.18293'],
            ["transform", "scaleX", '0.18293'],
            ["style", "opacity", '0'],
            ["style", "left", '-82px']
         ],
         "${_huajuan3}": [
            ["style", "top", '180px'],
            ["transform", "scaleY", '0.5'],
            ["transform", "scaleX", '0.5'],
            ["style", "left", '-82px'],
            ["style", "display", 'none']
         ]
      }
   },
   timelines: {
      "Default Timeline": {
         fromState: "Base State",
         toState: "",
         duration: 625,
         autoPlay: true,
         timeline: [
            { id: "eid28", tween: [ "style", "${_huajuan1}", "top", '111px', { fromValue: '122px'}], position: 0, duration: 250 },
            { id: "eid42", tween: [ "style", "${_huajuan1}", "top", '99px', { fromValue: '111px'}], position: 250, duration: 125 },
            { id: "eid51", tween: [ "style", "${_huajuan1}", "top", '-21px', { fromValue: '99px'}], position: 375, duration: 250 },
            { id: "eid10", tween: [ "style", "${_huajuan4}", "opacity", '1', { fromValue: '0.000000'}], position: 0, duration: 250 },
            { id: "eid81", tween: [ "style", "${_huajuan3}", "top", '180px', { fromValue: '180px'}], position: 250, duration: 0 },
            { id: "eid59", tween: [ "style", "${_huajuan3}", "top", '260px', { fromValue: '180px'}], position: 375, duration: 250 },
            { id: "eid80", tween: [ "style", "${_huajuan5}", "top", '172px', { fromValue: '182px'}], position: 250, duration: 125 },
            { id: "eid73", tween: [ "style", "${_huajuan5}", "top", '169px', { fromValue: '172px'}], position: 375, duration: 12 },
            { id: "eid75", tween: [ "style", "${_huajuan5}", "top", '-42px', { fromValue: '169px'}], position: 387, duration: 238 },
            { id: "eid15", tween: [ "transform", "${_huajuan4}", "scaleX", '0.5', { fromValue: '0.18293'}], position: 0, duration: 250 },
            { id: "eid88", tween: [ "transform", "${_symbol_content}", "scaleY", '1.15829', { fromValue: '1.15829'}], position: 625, duration: 0 },
            { id: "eid45", tween: [ "style", "${_huajuan3}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
            { id: "eid46", tween: [ "style", "${_huajuan3}", "display", 'block', { fromValue: 'none'}], position: 250, duration: 0 },
            { id: "eid48", tween: [ "style", "${_huajuan5}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
            { id: "eid49", tween: [ "style", "${_huajuan5}", "display", 'block', { fromValue: 'none'}], position: 250, duration: 0 },
            { id: "eid47", tween: [ "style", "${_huajuan5}", "display", 'block', { fromValue: 'block'}], position: 375, duration: 0 },
            { id: "eid85", tween: [ "style", "${_Stage}", "height", '327px', { fromValue: '327px'}], position: 625, duration: 0 },
            { id: "eid32", tween: [ "style", "${_huajuan2}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
            { id: "eid33", tween: [ "style", "${_huajuan2}", "display", 'block', { fromValue: 'none'}], position: 250, duration: 0 },
            { id: "eid16", tween: [ "transform", "${_huajuan4}", "scaleY", '0.5', { fromValue: '0.18293'}], position: 0, duration: 250 },
            { id: "eid25", tween: [ "style", "${_huajuan4}", "top", '152px', { fromValue: '135px'}], position: 0, duration: 250 },
            { id: "eid44", tween: [ "style", "${_huajuan4}", "top", '177px', { fromValue: '152px'}], position: 250, duration: 125 },
            { id: "eid53", tween: [ "style", "${_huajuan4}", "top", '257px', { fromValue: '177px'}], position: 375, duration: 250 },
            { id: "eid89", tween: [ "style", "${_symbol_content}", "top", '58px', { fromValue: '58px'}], position: 625, duration: 0 },
            { id: "eid76", tween: [ "style", "${_huajuan2}", "top", '150px', { fromValue: '162px'}], position: 250, duration: 125 },
            { id: "eid56", tween: [ "style", "${_huajuan2}", "top", '30px', { fromValue: '150px'}], position: 375, duration: 250 },
            { id: "eid6", tween: [ "style", "${_huajuan1}", "opacity", '1', { fromValue: '0.000000'}], position: 0, duration: 250 },
            { id: "eid61", tween: [ "style", "${_huajuan5}", "height", '38px', { fromValue: '3px'}], position: 375, duration: 12 },
            { id: "eid74", tween: [ "style", "${_huajuan5}", "height", '431px', { fromValue: '38px'}], position: 387, duration: 238 },
            { id: "eid21", tween: [ "transform", "${_huajuan1}", "scaleX", '0.5', { fromValue: '0.18293'}], position: 0, duration: 250 },
            { id: "eid22", tween: [ "transform", "${_huajuan1}", "scaleY", '0.5', { fromValue: '0.18293'}], position: 0, duration: 250 }         ]
      }
   }
},
"symbol_content": {
   version: "1.5.0",
   minimumCompatibleVersion: "1.5.0",
   build: "1.5.0.217",
   baseState: "Base State",
   initialState: "Base State",
   gpuAccelerate: false,
   resizeInstances: false,
   content: {
   dom: [
   {
      rect: ['0px','0px','196px','199px','auto','auto'],
      type: 'rect',
      id: 'Rectangle',
      stroke: [0,'rgba(0,0,0,1)','none'],
      display: 'none',
      fill: ['rgba(192,192,192,1)']
   }],
   symbolInstances: [
   ]
   },
   states: {
      "Base State": {
         "${symbolSelector}": [
            ["style", "height", '199px'],
            ["style", "width", '196px']
         ],
         "${_Rectangle}": [
            ["style", "display", 'none'],
            ["style", "height", '199px'],
            ["style", "left", '0px'],
            ["style", "top", '0px']
         ]
      }
   },
   timelines: {
      "Default Timeline": {
         fromState: "Base State",
         toState: "",
         duration: 625,
         autoPlay: true,
         timeline: [
            { id: "eid82", tween: [ "style", "${_Rectangle}", "display", 'none', { fromValue: 'none'}], position: 0, duration: 0 },
            { id: "eid83", tween: [ "style", "${_Rectangle}", "display", 'block', { fromValue: 'none'}], position: 625, duration: 0 }         ]
      }
   }
}
};


Edge.registerCompositionDefn(compId, symbols, fonts, resources);

/**
 * Adobe Edge DOM Ready Event Handler
 */
$(window).ready(function() {
     Edge.launchComposition(compId);
});
})(jQuery, AdobeEdge, "EDGE-4191738");
