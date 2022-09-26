using AutoMapper;
using Carer.Common;
using Carer.Model.DomainModels;
using Carer.Service.Common;
using CarerServer.WebAPI.Models.InsertModels;
using CarerServer.WebAPI.Models.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;

namespace CarerServer.WebAPI.Controllers
{
    public class AdController : ApiController
    {
        private IAdService AdService;
        private IMapper mapper;

        public AdController(IAdService AdService, IMapper mapper)
        {
            this.AdService = AdService;
            this.mapper = mapper;
        }


        public async Task<HttpResponseMessage> GetAdsAsync([FromUri]AdFilter filter)
        {

            if (filter.Type == 1)
            {
                List<OwnerAdViewModel> Ads = mapper.Map<List<AdDomainModel>, List<OwnerAdViewModel>>(await AdService.GetAllAdsAsync(filter));
                return Request.CreateResponse(HttpStatusCode.OK, Ads);
            }
            else
            {
                List<CarerAdViewModel> Ads = mapper.Map<List<AdDomainModel>, List<CarerAdViewModel>>(await AdService.GetAllAdsAsync(filter));
                return Request.CreateResponse(HttpStatusCode.OK, Ads);
            }
            
            
        }

        public async Task<HttpResponseMessage> GetAdAsync(Guid id,[FromUri] Guid userId)
        {
            AdDomainModel ad = await AdService.GetAdByIdAsync(id,userId);

            if (ad.Type == 1)
            {
                OwnerAdViewModel Ad = mapper.Map<AdDomainModel, OwnerAdViewModel>(ad);
                return Request.CreateResponse(HttpStatusCode.OK, Ad);
            }
            else
            {
                CarerAdViewModel Ad = mapper.Map<AdDomainModel, CarerAdViewModel>(ad);
                return Request.CreateResponse(HttpStatusCode.OK, Ad);
            }

        }

        public async Task<HttpResponseMessage> PostAd(AdInputModel Ad)
        {

            await AdService.PostAdAsync(mapper.Map<AdInputModel, AdDomainModel>(Ad));
            return Request.CreateResponse(HttpStatusCode.OK);
        }

        public async Task<HttpResponseMessage> PutAdAsync(AdPutModel Ad)
        {
            AdDomainModel domainAd = mapper.Map<AdPutModel, AdDomainModel>(Ad);
            await AdService.PutAdAsync(domainAd);
            return Request.CreateResponse(HttpStatusCode.OK);
        }

        public async Task<HttpResponseMessage> DeleteAdAsync(Guid id)
        {
            await AdService.DeleteAdAsync(id);
            return Request.CreateResponse(HttpStatusCode.OK);
        }
    }
}