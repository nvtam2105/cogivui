package com.cogivui.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cogivui.criteria.EventCriteria;
import com.cogivui.gmap.AddressConverter;
import com.cogivui.gmap.GoogleResponse;
import com.cogivui.message.Status;
import com.cogivui.model.Event;
import com.cogivui.model.EventDto;
import com.cogivui.model.PeriodTime;
import com.cogivui.model.Place;
import com.cogivui.model.Time;
import com.cogivui.service.EventService;
import com.cogivui.utils.ControllerUtils;
import com.cogivui.utils.MapUtils;

@Controller
@RequestMapping("/event")
public class EventRestController {

    static final Logger logger = Logger.getLogger(EventRestController.class);

    @Autowired
    ServletContext context;

    @Autowired
    EventService eventService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Status addEvent(@RequestBody EventDto eventDto) {
	try {
	    Event clone = Event.clone(eventDto);
	    eventService.saveOrUpdate(clone);
	    return new Status(1, "Event added successfully !");
	} catch (Exception e) {
	    return new Status(0, e.toString());
	}

    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Status udpateEvent(@PathVariable("id") long id, @RequestBody EventDto eventDto) {
	try {

	    Event event = Event.clone(eventDto);
	    event.setId(id);
	    eventService.saveOrUpdate(event);
	    System.out.println("delete category, tags and update again");
	    return new Status(1, "Event updated successfully !");
	} catch (Exception e) {
	    return new Status(0, e.toString());
	}

    }

    @RequestMapping(value = "/getEventsUpComming/{latitude}/{longitude}/{cityId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Event> getEventsUpComing(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude,
	    @PathVariable("cityId") long cityId) {
	List<Event> events = null;
	try {
	    EventCriteria eventCriteria = new EventCriteria();
	    eventCriteria.setCityId(cityId);
	    events = eventService.getEventsUpComing(eventCriteria);
	    setDistanceForEvents(latitude, longitude, events);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return events;
    }

    @RequestMapping(value = "/getEventsUpComming/{latitude}/{longitude}/{cityId}/{offset}/{limit}", method = RequestMethod.GET)
    public @ResponseBody
    List<Event> getEventsUpComing(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude,
	    @PathVariable("cityId") long cityId, @PathVariable("offset") int offset, @PathVariable("limit") int limit) {
	List<Event> events = null;
	try {
	    EventCriteria eventCriteria = new EventCriteria();
	    eventCriteria.setCityId(cityId);
	    eventCriteria.setOffset(offset);
	    eventCriteria.setLimit(limit);

	    events = eventService.getEventsUpComing(eventCriteria);
	    setDistanceForEvents(latitude, longitude, events);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return events;
    }

    @RequestMapping(value = "/getEventsUpComming/{latitude}/{longitude}/{categoryId}/{cityId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Event> getEventsUpComing(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude,
	    @PathVariable("categoryId") long categoryId, @PathVariable("cityId") long cityId) {
	List<Event> events = null;
	try {
	    EventCriteria eventCriteria = new EventCriteria();
	    eventCriteria.setCategoryId(categoryId);
	    eventCriteria.setCityId(cityId);
	    events = eventService.getEventsUpComing(eventCriteria);
	    setDistanceForEvents(latitude, longitude, events);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return events;
    }

    @RequestMapping(value = "/getEventsUpComming/{latitude}/{longitude}/{categoryId}/{cityId}/{offset}/{limit}", method = RequestMethod.GET)
    public @ResponseBody
    List<Event> getEventsUpComing(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude,
	    @PathVariable("categoryId") long categoryId, @PathVariable("cityId") long cityId, @PathVariable("offset") int offset,
	    @PathVariable("limit") int limit) {
	List<Event> events = null;
	try {
	    EventCriteria eventCriteria = new EventCriteria();
	    eventCriteria.setCategoryId(categoryId);
	    eventCriteria.setCityId(cityId);
	    eventCriteria.setOffset(offset);
	    eventCriteria.setLimit(limit);

	    events = eventService.getEventsUpComing(eventCriteria);
	    setDistanceForEvents(latitude, longitude, events);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return events;
    }

    @RequestMapping(value = "/getEventsToday/{latitude}/{longitude}/{cityId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Event> getEventsToday(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude,
	    @PathVariable("cityId") long cityId) {
	List<Event> events = null;
	try {
	    EventCriteria eventCriteria = new EventCriteria();
	    eventCriteria.setCityId(cityId);
	    events = eventService.getEventsToday(eventCriteria);
	    setDistanceForEvents(latitude, longitude, events);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return events;
    }

    @RequestMapping(value = "/getEventsToday/{latitude}/{longitude}/{cityId}/{offset}/{limit}", method = RequestMethod.GET)
    public @ResponseBody
    List<Event> getEventsToday(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude,
	    @PathVariable("cityId") long cityId, @PathVariable("offset") int offset, @PathVariable("limit") int limit) {
	List<Event> events = null;
	try {
	    EventCriteria eventCriteria = new EventCriteria();
	    eventCriteria.setCityId(cityId);
	    eventCriteria.setOffset(offset);
	    eventCriteria.setLimit(limit);

	    events = eventService.getEventsToday(eventCriteria);
	    setDistanceForEvents(latitude, longitude, events);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return events;
    }

    @RequestMapping(value = "/getEventsToday/{latitude}/{longitude}/{categoryId}/{cityId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Event> getEventsToday(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude,
	    @PathVariable("categoryId") long categoryId, @PathVariable("cityId") long cityId) {
	List<Event> events = null;
	try {
	    EventCriteria eventCriteria = new EventCriteria();
	    eventCriteria.setCityId(cityId);
	    eventCriteria.setCategoryId(categoryId);

	    events = eventService.getEventsToday(eventCriteria);
	    setDistanceForEvents(latitude, longitude, events);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return events;
    }

    @RequestMapping(value = "/getEventsToday/{latitude}/{longitude}/{categoryId}/{cityId}/{offset}/{limit}", method = RequestMethod.GET)
    public @ResponseBody
    List<Event> getEventsToday(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude,
	    @PathVariable("categoryId") long categoryId, @PathVariable("cityId") long cityId, @PathVariable("offset") int offset,
	    @PathVariable("limit") int limit) {
	List<Event> events = null;
	try {
	    EventCriteria eventCriteria = new EventCriteria();
	    eventCriteria.setCategoryId(categoryId);
	    eventCriteria.setCityId(cityId);
	    eventCriteria.setOffset(offset);
	    eventCriteria.setLimit(limit);

	    events = eventService.getEventsToday(eventCriteria);
	    setDistanceForEvents(latitude, longitude, events);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return events;
    }

    @RequestMapping(value = "/getEventsTomorrow/{latitude}/{longitude}/{cityId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Event> getEventsTomorrow(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude,
	    @PathVariable("cityId") long cityId) {
	List<Event> events = null;
	try {
	    EventCriteria eventCriteria = new EventCriteria();
	    eventCriteria.setCityId(cityId);
	    events = eventService.getEventsTomorrow(eventCriteria);
	    setDistanceForEvents(latitude, longitude, events);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return events;
    }

    @RequestMapping(value = "/getEventsTomorrow/{latitude}/{longitude}/{cityId}/{offset}/{limit}", method = RequestMethod.GET)
    public @ResponseBody
    List<Event> getEventsTomorrow(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude,
	    @PathVariable("cityId") long cityId, @PathVariable("offset") int offset, @PathVariable("limit") int limit) {
	List<Event> events = null;
	try {
	    EventCriteria eventCriteria = new EventCriteria();
	    eventCriteria.setCityId(cityId);
	    eventCriteria.setOffset(offset);
	    eventCriteria.setLimit(limit);

	    events = eventService.getEventsTomorrow(eventCriteria);
	    setDistanceForEvents(latitude, longitude, events);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return events;
    }

    @RequestMapping(value = "/getEventsTomorrow/{latitude}/{longitude}/{categoryId}/{cityId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Event> getEventsTomorrow(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude,
	    @PathVariable("categoryId") long categoryId, @PathVariable("cityId") long cityId) {
	List<Event> events = null;
	try {
	    EventCriteria eventCriteria = new EventCriteria();
	    eventCriteria.setCityId(cityId);
	    ;
	    eventCriteria.setCategoryId(categoryId);

	    events = eventService.getEventsTomorrow(eventCriteria);
	    setDistanceForEvents(latitude, longitude, events);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return events;
    }

    @RequestMapping(value = "/getEventsTomorrow/{latitude}/{longitude}/{categoryId}/{cityId}/{offset}/{limit}", method = RequestMethod.GET)
    public @ResponseBody
    List<Event> getEventsTomorrow(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude,
	    @PathVariable("categoryId") long categoryId, @PathVariable("cityId") long cityId, @PathVariable("offset") int offset,
	    @PathVariable("limit") int limit) {
	List<Event> events = null;
	try {
	    EventCriteria eventCriteria = new EventCriteria();
	    eventCriteria.setCityId(cityId);
	    eventCriteria.setCategoryId(categoryId);
	    eventCriteria.setOffset(offset);
	    eventCriteria.setLimit(limit);

	    events = eventService.getEventsTomorrow(eventCriteria);
	    setDistanceForEvents(latitude, longitude, events);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return events;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Event getEvent(@PathVariable("id") long id) {
	Event event = null;
	try {
	    event = eventService.getById(id);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return event;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<Event> getEvent() {
	List<Event> events = null;
	try {
	    events = eventService.getEvents();

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return events;
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    Status deleteEvent(@PathVariable("id") long id) {
	try {
	    eventService.deleteById(id);
	    return new Status(1, "Event deleted successfully !");
	} catch (Exception e) {
	    return new Status(0, e.toString());
	}

    }

    @RequestMapping(value = "/uploadPoster", method = RequestMethod.POST)
    public @ResponseBody
    Status uploadPoster(@RequestParam(value = "file") MultipartFile file) {
	String name = file.getOriginalFilename();
	System.out.println(name);
	if (!file.isEmpty()) {
	    try {

		String rootPath = context.getRealPath("/resources/event_image");
		String pathname = "event_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(name);
		File poster = new File(rootPath + File.separator + pathname);
		FileUtils.writeByteArrayToFile(poster, file.getBytes());
		logger.info("Server File Location=" + poster.getAbsolutePath());
		return new Status(1, pathname);
	    } catch (Exception e) {
		return new Status(0, "You failed to upload " + name + " => " + e.getMessage());
	    }
	} else {
	    return new Status(0, "You failed to upload " + name + " because the file was empty.");
	}
    }

    @RequestMapping(value = "/uploadFileEvent", method = RequestMethod.POST)
    public @ResponseBody
    Status uploadFileEvent(@RequestParam(value = "file") MultipartFile file) {
	String name = file.getOriginalFilename();
	if (!file.isEmpty()) {
	    List<Event> list = new ArrayList<Event>();
	    // select_event select_event-href name time address longlat venue
	    // desc_full desc_short image_url page click page click-href
	    String[] FILE_HEADER_MAPPING = { "select_event", "select_event-href", "name", "time", "address", "latlong", "venue",
		    "desc_full", "desc_short", "image_url", "page click-href" };
	    CSVParser csvFileParser = null;
	    Reader fileReader = null;
	    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
	    try {
		fileReader = new InputStreamReader(file.getInputStream(), "UTF-8");
		csvFileParser = new CSVParser(fileReader, csvFileFormat);
		List<CSVRecord> csvRecords = csvFileParser.getRecords();
		for (int i = 1; i < csvRecords.size(); i++) {
		    CSVRecord record = csvRecords.get(i);
		    Event event = new Event();
		    event.setName(record.get("name"));
		    // event.setHotline(record.get("hotline"));
		    event.setVenueName(record.get("venue"));
		    // event.setTime(new Time(record.get("time")));
		    Time time = new Time();
		    String timeStr = StringUtils.trim(StringUtils.remove(record.get("time"), "\n").replaceAll("\\s+", " "));
		    if (StringUtils.isNoneBlank(timeStr)) {
			if (timeStr.contains("Today") || timeStr.contains(" Tomorrow")) {
			    if (timeStr.contains("Today")) {
				time.setStartDate(new Date());
				time.setEndDate(new Date());
				String[] dateTimeHourMin = StringUtils.split(timeStr, ",");
				if (dateTimeHourMin.length > 1) {
				    String[] hoursMin = StringUtils.split(StringUtils.trim(dateTimeHourMin[1]), ":");
				    if (hoursMin.length > 1) {
					time.setHourStartDate(Integer.valueOf(hoursMin[0]));
					time.setMinStartDate(Integer.valueOf(hoursMin[1]));
					if (Integer.valueOf(hoursMin[0]) >= 12) {
					    time.setStartPeriod(PeriodTime.PM);
					} else {
					    time.setStartPeriod(PeriodTime.AM);
					}
				    }
				}
			    }

			    if (timeStr.contains("Tomorrow")) {
				Date tomorrow = DateUtils.addDays(new Date(), 1);
				time.setStartDate(tomorrow);
				time.setEndDate(tomorrow);
				String[] dateTimeHourMin = StringUtils.split(timeStr, ",");
				if (dateTimeHourMin.length > 1) {
				    String[] hoursMin = StringUtils.split(StringUtils.trim(dateTimeHourMin[1]), ":");
				    if (hoursMin.length > 1) {
					time.setHourStartDate(Integer.valueOf(hoursMin[0]));
					time.setMinStartDate(Integer.valueOf(hoursMin[1]));
					if (Integer.valueOf(hoursMin[0]) >= 12) {
					    time.setStartPeriod(PeriodTime.PM);
					} else {
					    time.setStartPeriod(PeriodTime.AM);
					}
				    }
				}
			    }
			    event.setTime(time);

			} else {
			    String[] dateTimeHourMin = StringUtils.split(timeStr, ",");
			    if (dateTimeHourMin.length > 1) {
				SimpleDateFormat dt = new SimpleDateFormat("d MMM yyyy");
				if (StringUtils.split(dateTimeHourMin[1], " ").length <= 2) {
				    Calendar now = Calendar.getInstance();
				    int year = now.get(Calendar.YEAR);
				    dateTimeHourMin[1] = StringUtils.trim(dateTimeHourMin[1]) + " " + year;
				}
				Date date = dt.parse(dateTimeHourMin[1]);
				time.setStartDate(date);
				time.setEndDate(date);
				if (dateTimeHourMin.length > 2) {
				    String[] hoursMin = StringUtils.split(StringUtils.trim(dateTimeHourMin[2]), ":");
				    if (hoursMin.length > 1) {
					time.setHourStartDate(Integer.valueOf(hoursMin[0]));
					time.setMinStartDate(Integer.valueOf(hoursMin[1]));
					if (Integer.valueOf(hoursMin[0]) >= 12) {
					    time.setStartPeriod(PeriodTime.PM);
					} else {
					    time.setStartPeriod(PeriodTime.AM);
					}
				    }
				}
			    }
			    event.setTime(time);
			}

		    }

		    String des = StringUtils.isBlank(record.get("desc_full")) ? ControllerUtils.br2nl(record.get("desc_full").trim())
			    : ControllerUtils.br2nl(StringUtils.trim(record.get("desc_short")));
		    event.setDescription(des);

		    if (StringUtils.isNoneBlank(record.get("latlong"))) {
			Place place = new Place();
			String[] lalong = StringUtils.trim(record.get("latlong")).split(",");
			place.setLatitude(lalong[0]);
			place.setLongitude(lalong[1]);
			try {
			    GoogleResponse res1 = new AddressConverter().convertFromLatLong(StringUtils.trim(record.get("latlong")));
			    if ("OK".equals(res1.getStatus()) && res1.getResults().length > 0) {
				place.setAddress(res1.getResults()[0].getFormatted_address());
			    } else if ("OVER_QUERY_LIMIT".equals(res1.getStatus())) {
				System.out.println(res1.getStatus());
				Thread.sleep(5000);
				GoogleResponse res2 = new AddressConverter().convertFromLatLong(StringUtils.trim(record.get("latlong")));
				if ("OK".equals(res2.getStatus()) && res2.getResults().length > 0) {
				    place.setAddress(res2.getResults()[0].getFormatted_address());
				}
			    }
			} catch (IOException e) {
			    System.out.println(e.getMessage());
			}

			if (place.getAddress().contains(URLDecoder.decode("H%E1%BB%93+Ch%C3%AD+Minh", "UTF-8"))) {
			    place.setCityId(1); // HCM
			} else if (place.getAddress().contains(URLDecoder.decode("H%C3%A0+N%E1%BB%99i", "UTF-8"))) {
			    place.setCityId(2);// HN
			} else if (place.getAddress().contains(URLDecoder.decode("%C4%90%C3%A0+N%E1%BA%B5ng", "UTF-8"))) {
			    place.setCityId(3);// DN
			} else {
			    place.setCityId(0);// None
			}
			event.setPlace(place);
		    }

		    // event.setPrice(Float.valueOf(record.get("price")));
		    event.setPosterUrl(record.get("image_url"));
		    event.setOriginalUrl(record.get("select_event-href"));
		    event.setStatus(0);
		    // event.setOrganizerContact(new OrganizerContact());

		    // city ==> send long, lat to Google ==> extract
		    // category, hotline, price, time
		    list.add(event);
		    System.out.println(record);
		}
		eventService.saveOrUpdate(list);
		return new Status(1, "Event was inputted successfully");
	    } catch (Exception e) {
		return new Status(0, "You failed to upload " + name + " => " + e.getMessage());
	    } finally {
		try {
		    fileReader.close();
		    csvFileParser.close();
		} catch (IOException e) {
		    System.out.println("Error while closing fileReader/csvFileParser !!!");
		    e.printStackTrace();
		}
	    }
	} else {
	    return new Status(0, "You failed to upload " + name + " because the file was empty.");
	}
    }

    public static void main(String[] args) {
	String oldstring = "16 December 2015";
	SimpleDateFormat dt = new SimpleDateFormat("d MMM yyyy");
	try {
	    Date date = dt.parse(oldstring);
	    System.out.println(date);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private void setDistanceForEvents(double latitude, double longitude, List<Event> events) {
	for (Event e : events) {
	    e.setDistance(MapUtils.distVincenty(latitude, longitude, Double.valueOf(e.getPlace().getLatitude()),
		    Double.valueOf(e.getPlace().getLongitude())));
	}
    }
}
