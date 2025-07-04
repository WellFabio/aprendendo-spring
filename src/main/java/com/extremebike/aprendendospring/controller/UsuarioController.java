package com.extremebike.aprendendospring.controller;

import com.extremebike.aprendendospring.business.UsuarioService;
import com.extremebike.aprendendospring.controller.dtos.UsusarioDTO;
import com.extremebike.aprendendospring.infrastructure.entity.Usuario;
import com.extremebike.aprendendospring.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

   private final UsuarioService usuarioService;
   private final AuthenticationManager authenticationManager;
   private final JwtUtil jwtUtil;

   @PostMapping
   public ResponseEntity<Usuario> salvaUsuario(@RequestBody Usuario usuario){
       return ResponseEntity.ok(usuarioService.salvaUsuario(usuario));
   }

   @PostMapping("/login")
    public String login(@RequestBody UsusarioDTO usuarioDTO){
       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),usuarioDTO.getSenha())
       );
       return "Bearer " + jwtUtil.generateToken(authentication.getName());
   }

   @GetMapping

   public ResponseEntity<Usuario> buscaUsuarioPorEmail(@RequestParam("email") String email) {
      return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
   }

   @DeleteMapping("/{email}")
   public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email) {
      usuarioService.deletaUsuarioPOrEmail(email);
      return ResponseEntity.ok() .build();
   }
}
