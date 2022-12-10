package com.digital.crud.saladereuniao.controller;
import com.digital.crud.saladereuniao.exception.ResourcesNotFoundException;
import com.digital.crud.saladereuniao.model.Room;
import com.digital.crud.saladereuniao.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RoomController {
    @Autowired
   private RoomRepository roomRepository;
    @GetMapping("rooms")
   public List<Room> getAllRooms(){
       return roomRepository.findAll();
   }

   @GetMapping("/rooms/id")
   public ResponseEntity<Room> getRoomById(@PathVariable(value = "id")long roomId) throws ResourcesNotFoundException{
        Room room = roomRepository.findById(roomId).orElseThrow(()-> new ResourcesNotFoundException("Room not found:: " + roomId) );
        return ResponseEntity.ok().body(room);
   }

   @PostMapping("/rooms")
   public Room createRoom(@Valid @RequestBody Room room){
        return roomRepository.save(room);
   }

   @PutMapping("/rooms/{id}")
   public ResponseEntity<Room> updateRoom(@PathVariable(value = "id") long roomId, @Valid @RequestBody Room roomDetails) throws ResourcesNotFoundException {
       Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourcesNotFoundException("Room not Found for ths id::" + roomId));
       room.setNome(roomDetails.getNome());
       room.setData(roomDetails.getData());
       room.setStartHour(roomDetails.getStartHour());
       room.setEndHour(room.getEndHour());
       final Room updateRoom = roomRepository.save(room);
       return ResponseEntity.ok(updateRoom);
   }

    public Map< String , Boolean>  deleteRoom(@PathVariable(value = "id")long roomId) throws ResourcesNotFoundException{
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourcesNotFoundException("Room not Found for ths id::" + roomId)) ;
        roomRepository.delete(room);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

