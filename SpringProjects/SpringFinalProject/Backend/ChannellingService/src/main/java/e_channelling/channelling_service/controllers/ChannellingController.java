package e_channelling.channelling_service.controllers;

import e_channelling.channelling_service.dto.ChannellingDto;
import e_channelling.channelling_service.dto.ChannellingSearchByIdsDto;
import e_channelling.channelling_service.dto.ChannellingSearchDTO;
import e_channelling.channelling_service.dto.ResponseDto;
import e_channelling.channelling_service.models.Channelling;
import e_channelling.channelling_service.servicers.ChannellingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/channelling")
public class ChannellingController {

    @Autowired
    ChannellingService channellingService;

    @PreAuthorize("hasRole('ROLE_operator')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseDto save(@RequestBody Channelling channelling, @RequestHeader("Authorization") String token, Principal principal) {

        try {

            return channellingService.save(channelling,token,principal.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Channelling Save Failed.!");
        }

    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseDto update(@RequestBody Channelling channelling) {

        try {

            return channellingService.update(channelling);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Channelling Update Error.!");
        }

    }

//    Accessing [Manager]
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseDto delete(@PathVariable int id, @RequestHeader("Authorization") String token, Principal principal) {

        try {

            return channellingService.delete(id, token, principal.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Channelling Delete Failed.!");
        }

    }


    //    Accessing [Manager]
    @RequestMapping(method = RequestMethod.GET, value = "/startChannelling/{id}")
    public ResponseDto startChannelling(@PathVariable int id, @RequestHeader("Authorization") String token, Principal principal) {

        try {

            return channellingService.startChannelling(id, token, principal.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Channelling Delete Failed.!");
        }

    }

    //    Accessing [Manager]
    @RequestMapping(method = RequestMethod.GET, value = "/finishChannelling/{id}")
    public ResponseDto finishChannelling(@PathVariable int id, @RequestHeader("Authorization") String token, Principal principal) {

        try {

            return channellingService.finishChannelling(id, token, principal.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return new ResponseDto(false, "Channelling Delete Failed.!");
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/search")
    public List<ChannellingDto> search(@RequestBody ChannellingSearchDTO channellingSearchDTO) {
//    public List<ChannellingDto> search() {

        try {

//            return channellingService.search(channelling);
            return channellingService.search(channellingSearchDTO);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/searchByHospital")
    public List<ChannellingDto> searchByHospital(@RequestBody ChannellingSearchDTO channellingSearchDTO, Principal principal, @RequestHeader("Authorization") String token) {
//    public List<ChannellingDto> search() {

        try {

//            return channellingService.search(channelling);
            return channellingService.searchByHospital(channellingSearchDTO, principal.getName(),token);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/findChannellingsByIds")
    public List<ChannellingDto> findChannellingsByIds(@RequestBody ChannellingSearchByIdsDto channellingSearchByIdsDto) {

        try {
            return channellingService.findChannellingsByIds(channellingSearchByIdsDto);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log exception");
            return null;
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Channelling test() {

        return new Channelling(1, 1, 1, "34", 1200.00, Instant.now(), Instant.now().plusSeconds(60 * 60 * 2), "1");

    }

    @RequestMapping(method = RequestMethod.GET, value = "/findByIdAndStatus/{id}")
    public Boolean findByIdAndStatus(@PathVariable Integer id) {
        return channellingService.findByIdAndStatus(id, "1");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findById/{id}")
    public Channelling findById(@PathVariable Integer id) {
        return channellingService.findById(id);
    }

}
