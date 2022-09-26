using AutoMapper;
using Carer.Model.DomainModels;
using CarerServer.WebAPI.Models.InsertModels;
using CarerServer.WebAPI.Models.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarerServer.WebAPI.App_Start
{
    public class AutoMapperProfile : Profile
    {
        public AutoMapperProfile()
        {
            CreateMap<UserDomainModel, UserViewModel>().ReverseMap();
            CreateMap<PetDomainModel, PetViewModel>().ReverseMap();
            CreateMap<PetInputModel, PetDomainModel>();
            CreateMap<AdInputModel, AdDomainModel>();
            CreateMap<AdPutModel, AdDomainModel>();
            CreateMap<AdDomainModel, OwnerAdViewModel>().ReverseMap();
            CreateMap<AdDomainModel, CarerAdViewModel>();
            CreateMap<AppliedUserDomainModel,AppliedUserViewModel>().ReverseMap();
            CreateMap<AppliedUserInputModel, AppliedUserDomainModel>().ReverseMap();

            CreateMap<CarerReviewInputModel, CarerReviewDomainModel>();
            CreateMap<OwnerReviewInputModel, OwnerReviewDomainModel>();
        }
    }
}