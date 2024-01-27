import React from 'react';
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from '@fullcalendar/timegrid';
import { EventInput } from '@fullcalendar/core/index.js';
import interactionPlugin from '@fullcalendar/interaction';
import { useState,useEffect , useRef } from "react";




const CalendarPlanification: React.FC = () => {


  const [events, setEvents] = useState<EventInput[]>([
    { title: "event 1", date: "2024-01-27" },
    { title: "event 2", date: "2024-01-28" },
  ]);

  const handleAddEvent = (arg: { dateStr: string }) => {
    console.log("Cliquei no dia: ", arg.dateStr);
  }

  const handleModalClose = () => {
    console.log("Modal fechado");
  }

  const handleEventClick = (info: EventInput) => {
    console.log("Cliquei no evento de: ", info.event);
  }

  const handleModalSubmit = (newEvent: EventInput) => {
    setEvents([...events, newEvent]);
  };
      
  
    return (
        <>
          <FullCalendar
            plugins={[ dayGridPlugin, interactionPlugin , timeGridPlugin]}
            initialView="timeGridWeek"
            headerToolbar={{
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay',
              }}
            locale="en"
            height={900}
            events={events}
            allDaySlot={false}
            eventStartEditable={true}
            eventResizableFromStart={true}
            eventDurationEditable={true}
            editable={true}
            droppable={true}
            slotDuration="00:30:00"
            slotMinTime={"06:00:00"}
            slotMaxTime={"23:00:00"}
            selectable={true}
            dayMaxEventRows={true}
            eventOverlap={true}
            select={(arg) => console.log(arg.startStr, arg.endStr)}
            // eventDragStop={(arg) => console.log(arg)}
            eventDrop={(arg) => console.log(arg.event.start)}
            dateClick={handleAddEvent}
            eventClick={handleEventClick}
          />
        </>
    );
};

export default CalendarPlanification;
