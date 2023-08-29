package com.application.main.features.user;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getUserForPageable(int pageNo, int pageSize, String sort) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sort).descending());
        return userRepository.findAll(paging);
    }

    public Page<User> getFilterOfUser(User filter, int pageNo, int pageSize, String sort) {
        if (filter != null) {
            System.out.println(filter);
            if (filter.getNombres_titular() != null || filter.getNombres_titular() != null ||
                    filter.getApellidos_b() != null || filter.getApellidos_titular() != null ||
                        filter.getCodigo() != null || filter.getActual_episodio() != null ||
                            filter.getCedula() != null || filter.getEstado() != null) {
                ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING)
                        .withIgnoreCase();

                Example<User> attributes = Example.of(filter, matcher);

                Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sort));
                return userRepository.findAll(attributes, paging);
            }

            Example<User> attributes = Example.of(filter);
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sort));

            return userRepository.findAll(attributes, paging);
        }

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sort).descending());
        return userRepository.findAll(paging);
    }

    public Page<User> getFilterUserByLateEpisode(int pageNo, int pageSize) {
        // obtener la fecha actual y comparla con la fecha del ultimo
        // episodio validar que hay 30 o mas de 30 para almacenar
        // los estudiantes en la lista y retornarla a el frontend
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return userRepository.findByDifferentDate(paging);
    }

    public List<User> getFilterOfUser(User filter) {
        Example<User> userTem = Example.of(filter);
        return userRepository.findAll(userTem);
    }

    public byte[] exportSheetExcel(User userFilter) throws Exception {
        String[] columns = {
                "ID",
                " ESTADO",
                "CEDULA",
                "CODIGO",
                "CONTRARO",
                "NOMBRES TITULAR",
                "APELLIDOS TITULAR",
                "CORREO",
                "DIRECCION",
                "CELULAR",
                "PLAN",
                "FECHA INICIO",
                "FECHA FINAL",
                "TOTAL",
                "CUOTA INICIAL",
                "SALDO",
                "VALOR CUOTA",
                "FECHA SALDO",
                "CEDULA BENEFICIARIO",
                "NOMBRES",
                "APELLIDOS",
                "SEXO",
                "CORREO",
                "EDAD",
                "FECHA NACIMIENTO",
                "DIRECCION",
                "CELULAR ESTUDIANTE",
                "CONSULTANTE",
                "ACTUAL EPISODIO",
                "ULTIMO EPISODIO",
                "INDUCCION",
                "CONGELAMIENTO",
                "OBSERVACIONES",
                "FECHA GRADUANDO",
                "TIPO ESTUDIANTE"
        };
        String path = "C:\\Users\\Juan\\Documents\\users.xls";

        HSSFWorkbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        HSSFSheet sheet = workbook.createSheet("STUDENTS");
        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            row.createCell(i).setCellValue(columns[i]);
        }

        List<User> listUsers;
        if (userFilter != null) {
            listUsers = this.getFilterOfUser(userFilter);
        } else {
            listUsers = this.getAllUsers();
        }

        int initRow = 1;
        for (User user : listUsers) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue((user.getEstado() == null) ? "" : user.getEstado());
            row.createCell(2).setCellValue(user.getCedula());
            row.createCell(3).setCellValue((user.getCodigo() == null) ? "" : user.getCodigo());
            row.createCell(4).setCellValue(user.getContrato());
            row.createCell(5).setCellValue(user.getNombres_titular());
            row.createCell(6).setCellValue(user.getApellidos_titular());
            row.createCell(7).setCellValue(user.getCorreo());
            row.createCell(8).setCellValue((user.getDireccion() == null) ? "" : user.getDireccion());
            row.createCell(9).setCellValue(user.getCelular());
            row.createCell(10).setCellValue(user.getPlan());
            row.createCell(11).setCellValue(user.getFecha_inicio().toString());
            row.createCell(12).setCellValue((user.getFecha_final() == null) ? null : user.getFecha_final().toString());
            row.createCell(13).setCellValue(user.getTotal());
            row.createCell(14).setCellValue((user.getCuota_inicial() == null) ? 0 : user.getCuota_inicial());
            row.createCell(15).setCellValue((user.getSaldo() == null) ? 0 : user.getSaldo());
            row.createCell(16).setCellValue(user.getValor_cuota());
            row.createCell(17).setCellValue((user.getFecha_pago() == null) ? null : user.getFecha_pago().toString());
            row.createCell(18).setCellValue(user.getCedula_beneficiario());
            row.createCell(19).setCellValue(user.getNombres_b());
            row.createCell(20).setCellValue(user.getApellidos_b());
            row.createCell(21).setCellValue(user.getSexo_b());
            row.createCell(22).setCellValue(user.getCorreo_b());
            row.createCell(23).setCellValue(user.getEdad_b());
            row.createCell(24).setCellValue((user.getFecha_nacimiento_b() == null) ? null : user.getFecha_nacimiento_b().toString());
            row.createCell(25).setCellValue((user.getDireccion_b() == null) ? "" : user.getDireccion_b());
            row.createCell(26).setCellValue(user.getTelefono_b());
            row.createCell(27).setCellValue((user.getConsultante_b() == null) ? "" : user.getConsultante_b());
            row.createCell(28).setCellValue((user.getActual_episodio() == null) ? "" : user.getActual_episodio());
            row.createCell(29).setCellValue((user.getUltimo_episodio() == null) ? null : user.getUltimo_episodio().toString());
            row.createCell(30).setCellValue((user.getInduccion_b() == null) ? null : user.getInduccion_b().toString());
            row.createCell(31).setCellValue((user.getCongelamiento() == null) ? null : user.getCongelamiento().toString());
            row.createCell(32).setCellValue((user.getObservaciones() == null) ? "" : user.getObservaciones());
            row.createCell(32).setCellValue((user.getFecha_graduando() == null) ? null : user.getFecha_graduando().toString());
            row.createCell(34).setCellValue((user.getTipoEstudiante() == null) ? "" : user.getTipoEstudiante());

            initRow++;
        }

        FileOutputStream fileOutput = new FileOutputStream(path);
        workbook.write(stream);
        stream.close();
        fileOutput.close();
        workbook.close();
        return stream.toByteArray();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Integer id, User user) {
        try {
            Optional<User> foundUser = userRepository.findById(id);

            if (foundUser.isPresent()) {
                User userUpdate = new User();
                userUpdate.setId(id);
                userUpdate.setEstado((user.getEstado() == null) ? foundUser.get().getEstado() : user.getEstado());
                userUpdate.setCedula((user.getCedula() == null) ? foundUser.get().getCedula() : user.getCedula());
                userUpdate.setCodigo((user.getCodigo() == null) ? foundUser.get().getCodigo() : user.getCodigo());
                userUpdate.setContrato((user.getContrato() == null) ? foundUser.get().getContrato() : user.getContrato());
                userUpdate.setNombres_titular((user.getNombres_titular() == null) ? foundUser.get().getNombres_titular() : user.getNombres_titular());
                userUpdate.setApellidos_titular((user.getApellidos_titular() == null) ? foundUser.get().getApellidos_titular() : user.getApellidos_titular());
                userUpdate.setCorreo((user.getCorreo() == null) ? foundUser.get().getCorreo() : user.getCorreo());
                userUpdate.setDireccion((user.getDireccion() == null) ? foundUser.get().getDireccion() : user.getDireccion());
                userUpdate.setCelular((user.getCelular() == null) ? foundUser.get().getCelular() : user.getCelular());
                userUpdate.setPlan((user.getPlan() == null) ? foundUser.get().getPlan() : user.getPlan());
                userUpdate.setFecha_inicio((user.getFecha_inicio() == null) ? foundUser.get().getFecha_inicio() : user.getFecha_inicio());
                userUpdate.setFecha_final((user.getFecha_final() == null) ? foundUser.get().getFecha_final() : user.getFecha_final());
                userUpdate.setTotal((user.getTotal() == null) ? foundUser.get().getTotal() : user.getTotal());
                userUpdate.setCuota_inicial((user.getCuota_inicial() == null) ? foundUser.get().getCuota_inicial() : user.getCuota_inicial());
                userUpdate.setSaldo((user.getSaldo() == null) ? foundUser.get().getSaldo() : user.getSaldo());
                userUpdate.setValor_cuota((user.getValor_cuota() == null) ? foundUser.get().getValor_cuota() : user.getValor_cuota());
                userUpdate.setFecha_pago((user.getFecha_pago() == null) ? foundUser.get().getFecha_pago() : user.getFecha_pago());
                userUpdate.setCedula_beneficiario((user.getCedula_beneficiario() == null) ? foundUser.get().getCedula_beneficiario() : user.getCedula_beneficiario());
                userUpdate.setNombres_b((user.getNombres_b() == null) ? foundUser.get().getNombres_b() : user.getNombres_b());
                userUpdate.setApellidos_b((user.getApellidos_b() == null) ? foundUser.get().getApellidos_b() : user.getApellidos_b());
                userUpdate.setSexo_b((user.getSexo_b() == null) ? foundUser.get().getSexo_b() : user.getSexo_b());
                userUpdate.setCorreo_b((user.getCorreo_b() == null) ? foundUser.get().getCorreo_b() : user.getCorreo_b());
                userUpdate.setEdad_b((user.getEdad_b() == null) ? foundUser.get().getEdad_b() : user.getEdad_b());
                userUpdate.setFecha_nacimiento_b((user.getFecha_nacimiento_b() == null) ? foundUser.get().getFecha_nacimiento_b() : user.getFecha_nacimiento_b());
                userUpdate.setDireccion_b((user.getDireccion_b() == null) ? foundUser.get().getDireccion_b() : user.getDireccion_b());
                userUpdate.setTelefono_b((user.getTelefono_b() == null) ? foundUser.get().getTelefono_b() : user.getTelefono_b());
                userUpdate.setConsultante_b((user.getConsultante_b() == null) ? foundUser.get().getConsultante_b() : user.getConsultante_b());
                userUpdate.setActual_episodio((user.getActual_episodio() == null) ? foundUser.get().getActual_episodio() : user.getActual_episodio());
                userUpdate.setUltimo_episodio((user.getUltimo_episodio() == null) ? foundUser.get().getUltimo_episodio() : user.getUltimo_episodio());
                userUpdate.setInduccion_b((user.getInduccion_b() == null) ? foundUser.get().getInduccion_b() : user.getInduccion_b());
                userUpdate.setCongelamiento((user.getCongelamiento() == null) ? foundUser.get().getCongelamiento() : user.getCongelamiento());
                userUpdate.setObservaciones((user.getObservaciones() == null) ? foundUser.get().getObservaciones() : user.getObservaciones());
                userUpdate.setFecha_graduando((user.getFecha_graduando() == null) ? foundUser.get().getFecha_graduando() : user.getFecha_graduando());
                userUpdate.setTipoEstudiante((user.getTipoEstudiante() == null) ? foundUser.get().getTipoEstudiante() : user.getTipoEstudiante());

                return userRepository.save(userUpdate);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No se encuentroy el usuario con el id: " + id);
            }
        } catch (ResponseStatusException e1) {
            throw new ResponseStatusException(e1.getStatus(), e1.getReason());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Se produjo un error interno");            
        }
    }

    public boolean deleteUser(Integer id) {
        Optional<User> u = userRepository.findById(id);

        if (u.isPresent()) {
            userRepository.delete(u.get());
            return true;
        } else {
            return false;
        }
    }
}
