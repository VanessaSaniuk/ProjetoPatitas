//package br.com.patitas.app.service.impl;
//
//import br.com.patitas.app.model.Owner;
//import br.com.patitas.app.repository.OwnerRepository;
//import br.com.patitas.app.service.OwnerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class OwnerServiceImpl implements OwnerService {
//
//    private final OwnerRepository repository;
//
//    @Autowired
//    public OwnerServiceImpl(OwnerRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public Owner createOwner(Owner owner) {
//        return repository.save(owner);
//    }
//
//}
