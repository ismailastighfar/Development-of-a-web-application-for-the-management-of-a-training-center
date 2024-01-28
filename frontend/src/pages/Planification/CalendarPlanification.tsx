import React from 'react';
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from '@fullcalendar/timegrid';
import { EventInput } from '@fullcalendar/core/index.js';
import interactionPlugin from '@fullcalendar/interaction';
import { useState,useEffect , useRef } from "react";
import Popup from '../../components/Popup';
import TrainingSession from '../../components/TrainingSession';




const CalendarPlanification: React.FC = () => {


const [IsOpenPopup, setIsOpenPopup] = useState(false);

  const [events, setEvents] = useState<EventInput[]>([
    { title: "event 1", date: "2024-01-27" },
    { title: "event 2", date: "2024-01-28" },
  ]);

  const handleEventClick = (info: EventInput) => {
    console.log("Cliquei no evento de: ", info.event);
  }

  const handleModalSubmit = (newEvent: EventInput) => {
    setEvents([...events, newEvent]);
  };

  const handleSelect = (arg: any) => {
    setIsOpenPopup(true);
  }

  const handleSaveOnClick = () => {
    setIsOpenPopup(false);
    alert("Event saved");
  }
  
  
    return (
        <>
          <FullCalendar
            plugins={[ dayGridPlugin, interactionPlugin , timeGridPlugin]}
            initialView="dayGridMonth"
            headerToolbar={{
                left: 'prev,next today NewEvent',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay saveEvent',
              }}
            firstDay={1}
            height={900}
            events={events}
            customButtons={{
              saveEvent: {
                text: "Save Events",
                click: function() {
                  console.log("Cliquei no botão de salvar eventos");
                }
              },
              NewEvent: {
                text: "New Event",
                click: function() {
                  console.log("Cliquei no botão de novo evento");
                }
              }
            }}
            eventStartEditable={true}
            eventDurationEditable={true}
            displayEventEnd={true}
            editable={true}
            droppable={true}
            slotMinTime={"06:00:00"}
            slotMaxTime={"23:00:00"}
            selectable={true}
            dayMaxEventRows={true}
            select={handleSelect}
            // eventDragStop={(arg) => console.log(arg)}
            eventDrop={(arg) => console.log(arg.event.start)}
            eventClick={handleEventClick}
          />
            {IsOpenPopup && (
              <Popup Header={<></>}
              Content={<TrainingSession trainingSessionId={0} trainingId={0} start={new Date()} end={new Date()} startTime={''} endTime={''} onSaveClicked={handleSaveOnClick}/>} 
              Actions={<></>} 
              IsOpen={true} OnClose={() => setIsOpenPopup(false)} />
            )}
        </>
    );
};

export default CalendarPlanification;
