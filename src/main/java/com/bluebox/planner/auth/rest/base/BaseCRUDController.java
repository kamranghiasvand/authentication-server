//package com.bluebox.planner.auth.rest.base;
//
//import com.peykasa.olapX.common.dto.HasKeyDTO;
//import com.peykasa.olapX.common.exception.GlobalException;
//import com.peykasa.olapX.common.util.ConvertUtil;
//import com.peykasa.olapX.persistence.entity.HasKeyEntity;
//import com.peykasa.olapX.persistence.enums.SortField;
//import com.peykasa.olapX.persistence.service.base.CommandService;
//import com.peykasa.olapX.rest.validation.ValidationFactory;
//
//import java.io.Serializable;
//
//
///**
// * @author kamran
// */
//public abstract class BaseCRUDController<E extends HasKeyEntity<I>, D extends HasKeyDTO<I>, F extends SortField, I extends Serializable> extends BaseQueryController<E, D, F, I> {
//
//
//    protected abstract CommandService<E, D> getCommandService();
//
//    protected abstract ValidationFactory<D> getValidationFactory();
//
//    protected D add(D dto) throws GlobalException {
//        getLogger().info("Creating {}", getEntityLabel());
//        getValidationFactory().getCreateCtx().validate(dto);
//        E added = getCommandService().create(dto);
//        getLogger().info("{} created", getEntityLabel());
//        return ConvertUtil.to(added, getDTOClass());
//    }
//
//    protected D edit(D dto) throws GlobalException {
//        getLogger().info("Updating {} '{}'", getEntityLabel(), dto);
//        getValidationFactory().getUpdateCtx().validate(dto);
//        E updated = getCommandService().update(dto);
//        getLogger().info("{} with id '{}' updated", getEntityLabel(), dto.getKey());
//        return ConvertUtil.to(updated, getDTOClass());
//    }
//
//}
